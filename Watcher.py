"""
Single File Watcher with Email Notification
===========================================
Monitors a specific file on a network share and sends email notifications
when that file is modified or replaced.
"""

import os
import time
import sys
import json
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler

# Import our email sender module
from email_sender import send_email_with_attachment

def load_config(config_path: str = "config.json") -> dict:
    """Load configuration."""
    script_dir = os.path.dirname(os.path.abspath(__file__))
    full_path = os.path.join(script_dir, config_path)
    try:
        with open(full_path, 'r', encoding='utf-8') as f:
            return json.load(f)
    except FileNotFoundError:
        return {}

class SingleFileHandler(FileSystemEventHandler):
    """
    Handler that only processes events for ONE specific filename.
    """
    def __init__(self, filename_to_watch):
        self.filename_to_watch = filename_to_watch
        self.last_processed_time = 0
        self.cooldown_seconds = 5

    def _process(self, event_path, event_type):
        """Check if the event matches our target file."""
        # Get the name of the file that triggered the event
        event_filename = os.path.basename(event_path)

        # CRITICAL: Ignore everything that isn't our target file
        if event_filename != self.filename_to_watch:
            return

        # Cooldown check (prevent duplicate emails for one save)
        current_time = time.time()
        if (current_time - self.last_processed_time) < self.cooldown_seconds:
            return
        
        self.last_processed_time = current_time

        print(f"\n[DETECTED] {event_type}: {event_filename}")
        print("-" * 40)
        
        # Wait briefly for file write/lock to release
        time.sleep(2)

        if os.path.exists(event_path):
            success = send_email_with_attachment(event_path)
            if success:
                print(f"[OK] Email sent for {event_filename}")
            else:
                print(f"[FAIL] Email sending failed")
        else:
            print("[ERR] File disappeared before sending.")
        
        print("-" * 40)

    def on_modified(self, event):
        if not event.is_directory:
            self._process(event.src_path, "Modified")

    def on_created(self, event):
        # Handles cases where the file is deleted and replaced
        if not event.is_directory:
            self._process(event.src_path, "Created/Replaced")

def watch_specific_file():
    config = load_config()
    target_file_path = config.get("watcher", {}).get("target_file_path", "")

    if not target_file_path:
        print("ERROR: 'target_file_path' is empty in config.json")
        return

    # 1. Split the full path into Directory and Filename
    watch_directory = os.path.dirname(target_file_path)
    target_filename = os.path.basename(target_file_path)

    if not os.path.exists(watch_directory):
        print(f"ERROR: The directory does not exist: {watch_directory}")
        return

    print("=" * 50)
    print("Single File Monitor Active")
    print("=" * 50)
    print(f"Watching Folder: {watch_directory}")
    print(f"Target File ONLY: {target_filename}")
    print("Events: Modify, Replace")
    print("Press Ctrl+C to stop.\n")

    # 2. Initialize Handler with the specific filename
    event_handler = SingleFileHandler(filename_to_watch=target_filename)
    observer = Observer()
    
    # 3. Watch the directory
    observer.schedule(event_handler, watch_directory, recursive=False)
    observer.start()

    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        observer.stop()
    except OSError:
        observer.stop()
    
    observer.join()

if __name__ == "__main__":
    watch_specific_file()

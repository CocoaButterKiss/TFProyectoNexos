"""
Network Folder Watcher with Email Notification (Watchdog Edition)
=================================================================
Monitors a remote network folder for new or modified files using 
event-driven monitoring and sends email notifications.
"""

import os
import time
import sys
import json
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler

# Import our email sender module
# Ensure email_sender.py is in the same directory
from email_sender import send_email_with_attachment

def load_watcher_config(config_path: str = "config.json") -> dict:
    """Load watcher configuration from config file."""
    script_dir = os.path.dirname(os.path.abspath(__file__))
    full_path = os.path.join(script_dir, config_path)
    
    try:
        with open(full_path, 'r', encoding='utf-8') as f:
            config = json.load(f)
        return config.get("watcher", {})
    except FileNotFoundError:
        return {}

class EmailHandler(FileSystemEventHandler):
    """
    Custom handler to process file system events.
    """
    def __init__(self):
        self.last_processed = {}
        self.cooldown_seconds = 5  # Time to wait before processing the same file again

    def _process_file(self, file_path, event_type):
        """Internal method to validate file and trigger email."""
        filename = os.path.basename(file_path)
        
        # 1. Ignore temporary files often created by Office/Editors (start with ~ or $)
        if filename.startswith("~") or filename.startswith("$") or filename.startswith("."):
            return

        # 2. Debounce/Cooldown check
        # 'on_modified' fires multiple times during a single save. 
        # We check if we just processed this file recently.
        current_time = time.time()
        if file_path in self.last_processed:
            if current_time - self.last_processed[file_path] < self.cooldown_seconds:
                return
        
        # Update last processed time
        self.last_processed[file_path] = current_time

        print(f"\n[{event_type.upper()}] Detected: {filename}")
        print("-" * 40)

        # 3. Wait for file write to complete (Network latency safety)
        # Sometimes the event fires before the bytes are fully written
        time.sleep(1) 

        try:
            success = send_email_with_attachment(file_path)
            if success:
                print(f"[OK] Email notification sent for: {filename}")
            else:
                print(f"[WARN] Email failed for: {filename}")
        except Exception as e:
            print(f"[ERROR] Failed to process file: {e}")
        
        print("-" * 40)

    def on_created(self, event):
        """Called when a file or directory is created."""
        if not event.is_directory:
            self._process_file(event.src_path, "New File")

    def on_modified(self, event):
        """Called when a file or directory is modified."""
        if not event.is_directory:
            self._process_file(event.src_path, "Modified")

def watch_network_folder(network_path: str = None):
    """
    Start the Watchdog Observer.
    """
    # Load config
    watcher_config = load_watcher_config()
    folder_path = network_path or watcher_config.get("network_folder", "")
    
    if not folder_path:
        print("ERROR: No network folder specified in config or method call.")
        return
    
    if not os.path.exists(folder_path):
        print(f"ERROR: The path '{folder_path}' cannot be found.")
        return

    print("=" * 50)
    print("Watchdog File Monitor Active")
    print("=" * 50)
    print(f"Target: {folder_path}")
    print("Events: Created, Modified")
    print("Press Ctrl+C to stop.\n")

    # Setup Watchdog
    event_handler = EmailHandler()
    observer = Observer()
    
    # schedule(event_handler, path, recursive=False)
    # recursive=False means it won't look inside subfolders
    observer.schedule(event_handler, folder_path, recursive=False)
    
    observer.start()

    try:
        while True:
            # Keep the main thread running so the observer thread stays alive
            time.sleep(1)
    except KeyboardInterrupt:
        print("\nStopping observer...")
        observer.stop()
    except OSError:
        print("\nNetwork connection lost.")
        observer.stop()
        
    observer.join()

if __name__ == "__main__":
    watch_network_folder()

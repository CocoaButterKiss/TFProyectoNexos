import time
import os
import shutil
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler

# --- CONFIGURATION ---
WATCH_DIRECTORY = r"C:\Users\Name\Downloads\InputFolder"
TARGET_FILENAME = "data.csv"

# Where you want the file to go
DESTINATION_DIRECTORY = r"C:\Users\Name\Documents\Processed"

class FileProcessor(FileSystemEventHandler):
    def on_created(self, event):
        """Triggers when a file is added for the first time or replaced."""
        self.process(event)

    def on_modified(self, event):
        """Triggers when the file content is changed/saved."""
        self.process(event)

    def process(self, event):
        # 1. Check if it's a file (not a folder)
        if event.is_directory:
            return

        # 2. Check if the filename matches our target
        filename = os.path.basename(event.src_path)
        if filename != TARGET_FILENAME:
            return

        print(f"\nEvent detected: {event.event_type} - {filename}")

        # 3. Wait for the file to finish writing (Avoid "File in use" errors)
        # We loop until the file size stops changing for 1 second
        file_path = event.src_path
        historical_size = -1
        
        while True:
            try:
                # If file was already moved by a previous trigger, stop.
                if not os.path.exists(file_path):
                    return 

                current_size = os.path.getsize(file_path)
                if current_size == historical_size:
                    break # File size is stable, ready to go
                
                historical_size = current_size
                print("Waiting for file write to complete...")
                time.sleep(1) 
            except OSError:
                return # File might be locked or gone

        # 4. Perform the Move (shutil)
        try:
            # Construct destination path
            destination_path = os.path.join(DESTINATION_DIRECTORY, filename)
            
            # If a file with the same name exists in destination, we overwrite it
            if os.path.exists(destination_path):
                os.remove(destination_path)

            shutil.move(file_path, destination_path)
            print(f"SUCCESS: Moved {filename} to {DESTINATION_DIRECTORY}")
            
            # --- ADD YOUR EXTRA AUTOMATION CODE HERE IF NEEDED ---
            
        except FileNotFoundError:
            # This happens if watchdog fired twice quickly and we already moved it.
            pass
        except PermissionError:
            print("ERROR: File is currently open in another program.")

if __name__ == "__main__":
    # Ensure directories exist
    if not os.path.exists(WATCH_DIRECTORY):
        os.makedirs(WATCH_DIRECTORY)
    if not os.path.exists(DESTINATION_DIRECTORY):
        os.makedirs(DESTINATION_DIRECTORY)

    event_handler = FileProcessor()
    observer = Observer()
    observer.schedule(event_handler, WATCH_DIRECTORY, recursive=False)

    print(f"Watching '{WATCH_DIRECTORY}' for '{TARGET_FILENAME}'...")
    print("Press Ctrl+C to stop.")
    
    observer.start()
    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        observer.stop()
    observer.join()

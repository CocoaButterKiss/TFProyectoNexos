import time
import os
import shutil
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler

# --- CONFIGURATION ---
WATCH_DIRECTORY = r"C:\Users\Name\Downloads\InputFolder"
TARGET_FILENAME = "data.csv" # The specific name you will rename the file to
DESTINATION_DIRECTORY = r"C:\Users\Name\Documents\Processed"

class FileProcessor(FileSystemEventHandler):
    
    def on_created(self, event):
        """Triggers when a file is pasted or created directly with the correct name."""
        if not event.is_directory and os.path.basename(event.src_path) == TARGET_FILENAME:
            print(f"Detected CREATION: {event.src_path}")
            self.process_file(event.src_path)

    def on_modified(self, event):
        """Triggers when you save content to the file."""
        if not event.is_directory and os.path.basename(event.src_path) == TARGET_FILENAME:
            print(f"Detected MODIFICATION: {event.src_path}")
            self.process_file(event.src_path)

    def on_moved(self, event):
        """Triggers when you RENAME 'New Text Document.txt' to 'data.csv'."""
        # For a move event, we check the DESTINATION path (the new name)
        if not event.is_directory and os.path.basename(event.dest_path) == TARGET_FILENAME:
            print(f"Detected RENAME/MOVE: {event.dest_path}")
            self.process_file(event.dest_path)

    def process_file(self, file_path):
        """Standard logic to wait for file availability and move it."""
        
        # 1. Wait for file write to complete (Debounce/Lock check)
        historical_size = -1
        while True:
            try:
                # If file vanished (e.g. moved by a previous trigger), stop.
                if not os.path.exists(file_path):
                    return 

                current_size = os.path.getsize(file_path)
                if current_size == historical_size:
                    break
                
                historical_size = current_size
                print("Waiting for file write to complete...")
                time.sleep(1) 
            except OSError:
                return # File locked/gone

        # 2. Perform the Move
        try:
            filename = os.path.basename(file_path)
            destination_path = os.path.join(DESTINATION_DIRECTORY, filename)
            
            # Overwrite if exists
            if os.path.exists(destination_path):
                os.remove(destination_path)

            shutil.move(file_path, destination_path)
            print(f"SUCCESS: Moved to {destination_path}\n")
            
        except (FileNotFoundError, PermissionError) as e:
            # Common errors if the event fired twice rapidly
            print(f"Skipping (File already moved or locked): {e}")

if __name__ == "__main__":
    # Ensure directories exist
    if not os.path.exists(WATCH_DIRECTORY):
        os.makedirs(WATCH_DIRECTORY)
    if not os.path.exists(DESTINATION_DIRECTORY):
        os.makedirs(DESTINATION_DIRECTORY)

    event_handler = FileProcessor()
    observer = Observer()
    observer.schedule(event_handler, WATCH_DIRECTORY, recursive=False)

    print(f"Watching '{WATCH_DIRECTORY}'...")
    print(f"Waiting for a file named: '{TARGET_FILENAME}'")
    
    observer.start()
    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        observer.stop()
    observer.join()

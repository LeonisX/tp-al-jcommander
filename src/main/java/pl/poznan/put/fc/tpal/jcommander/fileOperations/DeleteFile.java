package pl.poznan.put.fc.tpal.jcommander.fileOperations;

import javafx.beans.property.BooleanProperty;

import java.io.File;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class DeleteFile extends FileOperation {
    public DeleteFile(List<File> files, BooleanProperty isCanceledProperty) {
        super(files, isCanceledProperty);
    }

    @Override
    void execute() {
        for(File file: files) {
            if(this.isCanceledProperty.get()) {
                break;
            }
            if(file.exists()) {
                if(file.isDirectory()) {
                    this.deleteDirectory(file);
                } else {
                    progress.set(progress.getValue() + file.length());
                    file.delete();
                }
            }
        }
    }

    private void deleteDirectory(File directory) {
        File[] directoryContent = directory.listFiles();
        if(directoryContent != null) {
            for(File file : directoryContent) {
                if(isCanceledProperty.get()) {
                    break;
                }
                if(file.exists()) {
                    if(file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        progress.set(progress.getValue() + file.length());
                        file.delete();
                    }
                }
            }
        }
        directory.delete();
    }
}
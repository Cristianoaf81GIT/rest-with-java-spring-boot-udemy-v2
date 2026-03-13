package  br.com.cristianoaf81.services.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import br.com.cristianoaf81.config.FileStorageConfig;
import br.com.cristianoaf81.exception.FileStorageException;

@Service
public class FileStorageService {
  
  Logger logger = Logger.getLogger(FileStorageService.class.getName());

  private final Path fileStorageLocation;

  @Autowired
  public FileStorageService(FileStorageConfig fileStorageConfig) {
    Path path = Paths.get(fileStorageConfig.getUploadDir())
    .toAbsolutePath().normalize();
    this.fileStorageLocation = path;
    
    logger.info("File path = " + path.toAbsolutePath().toString());
   
    try {
      Files.createDirectories(this.fileStorageLocation);
    } catch(Exception e) {
      logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
      throw new FileStorageException("Could not create the directory where files will be stored!", e);
    }
  }

  public Path getFileStorageLocation() {
	 return fileStorageLocation;
  }

  public String storeFile(MultipartFile file) {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      
      if (fileName.contains("..")) {
        throw new FileStorageException("Sorry! File Name Contains an Invalid Path Sequence: " + fileName);
      } 

      Path targetLocation = this.fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return fileName;
    } catch (Exception e) {
      logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
      throw new FileStorageException("Could not store file: " + fileName + " .Please try again!", e);
    }
  }

}

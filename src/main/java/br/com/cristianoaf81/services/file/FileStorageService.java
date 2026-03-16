package  br.com.cristianoaf81.services.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import br.com.cristianoaf81.config.FileStorageConfig;
import br.com.cristianoaf81.exception.FileStorageException;

@Service
public class FileStorageService {
  
  private Logger logger = LoggerFactory.getLogger(FileStorageService.class.getName());

  private final Path fileStorageLocation;

  @Autowired
  public FileStorageService(FileStorageConfig fileStorageConfig) {
    Path path = Paths.get(fileStorageConfig.getUploadDir())
    .toAbsolutePath().normalize();
    this.fileStorageLocation = path;
    
    logger.info("File path = " + path.toAbsolutePath().toString());
   
    try {
      logger.info("Creating directory!");
      Files.createDirectories(this.fileStorageLocation);
    } catch(Exception e) {
      logger.error("Error while trying to create upload dir: " + e.getMessage());
      throw new FileStorageException("Could not create the directory where files will be stored!", e);
    }
  }

  public Path getFileStorageLocation() {
	 return fileStorageLocation;
  }

  public String storeFile(MultipartFile file) {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      logger.info("Saving File!");
      if (fileName.contains("..")) {
        logger.error("Invalid path sequence for store file!");
        throw new FileStorageException("Sorry! File Name Contains an Invalid Path Sequence: " + fileName);
      } 

      Path targetLocation = this.fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return fileName;
    } catch (Exception e) {
      logger.error("Error while trying to store file: " + e.getMessage());
      throw new FileStorageException("Could not store file: " + fileName + " .Please try again!", e);
    }
  }

}

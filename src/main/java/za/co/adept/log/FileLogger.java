package za.co.adept.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author matthewl
 */
public class FileLogger implements Logger {

    private final String LOG_FILES_BASE_DIRECTORY = "/var/log/adept/";

    private String logFileSubDir = "";
    
    private final String logFileName;

    private final String SIMPLE_LOG_FORMAT = "%s [%s]: %s";

    private final String EXCEPTION_LOG_FORMAT = "%s [%s]: %s \n %s";

    private PrintWriter logWriter;

    public FileLogger(final String logFileName) {
        this.logFileName = logFileName;
    }
    
    public FileLogger(final String logFileSubDir, final String logFileName) {
        this.logFileSubDir = (logFileSubDir + "/").replace("//", "/");
        this.logFileName = logFileName;
    }

    @Override
    public void log(Event entry) {
        try {
            
            final String fullLogFileDirectory = LOG_FILES_BASE_DIRECTORY + logFileSubDir;
            
            final String fullLogFilePath = fullLogFileDirectory + logFileName;
            
            final File logFileDir = new File(fullLogFileDirectory);
            
            if(!logFileDir.exists()){
                System.out.println("Creating log directories " + logFileDir);
                logFileDir.mkdirs();
            }
            
            final File logFile = new File(fullLogFilePath);

            if (!logFile.exists()) {
                System.out.println("Creating log file " + fullLogFilePath);
                logFile.createNewFile();
            }
            logWriter = new PrintWriter(new FileOutputStream(logFile, true));

            if(!entry.getThrowable().isPresent()){
                logWriter.println(String.format(SIMPLE_LOG_FORMAT, entry.getTimestamp(), entry.getLevel(), entry.getMessage()));
            }else{
                logWriter.println(String.format(EXCEPTION_LOG_FORMAT, entry.getTimestamp(), entry.getLevel(), entry.getMessage(), entry.getThrowable().get()));
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if(logWriter != null)
                logWriter.close();
        }
    }
}

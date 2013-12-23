package db.reader;

/**
 * Interface used to give support for future implementations (reading from files, sockets, etc)
 */
public interface IReader {
    public String getRawCommand();
}

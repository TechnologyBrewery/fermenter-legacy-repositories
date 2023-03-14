package org.bitbucket.fermenter.mda.reporting;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;

/**
 * Captures statistics about a single source code file generated by Fermenter.
 */
public final class FileStats {
    private final String filePath;
    private long size;
    private long lineCount;

    @JsonCreator
    public FileStats(@JsonProperty("filePath") String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the absolute file system path of the generated file
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @return the size in bytes of the generated file (NB not disk size)
     */
    public long getSize() {
        return size;
    }

    /**
     * @return the number of newlines in the generated file
     */
    public long getLineCount() {
        return lineCount;
    }

    /**
     * Adds bytes that will or would be written to the generated file
     *
     * @param bytesWritten the number of bytes written
     */
    public void addBytesWritten(long bytesWritten) {
        size += bytesWritten;
    }

    /**
     * Adds lines that will or would be written to the generated file
     *
     * @param lines the number of lines
     */
    public void addLinesWritten(long lines) {
        this.lineCount += lines;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FileStats fileStats = (FileStats) o;

        return new EqualsBuilder()
            .append(size, fileStats.size)
            .append(lineCount, fileStats.lineCount)
            .append(filePath, fileStats.filePath)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(filePath).toHashCode();
    }

    /**
     * Combines a collection of individual file stats into one stats object that aggregates across the collection.
     *
     * @param statsToAggregate the FileStats to aggregate
     *
     * @return the aggregated file stats
     */
    public static Aggregate aggregate(Collection<FileStats> statsToAggregate) {
        int fileCount = statsToAggregate.size();
        long totalSize = 0L;
        long totalLines = 0L;
        for (FileStats eachFileStats : statsToAggregate) {
            totalSize += eachFileStats.getSize();
            totalLines += eachFileStats.getLineCount();
        }
        return new Aggregate(fileCount, totalSize, totalLines);
    }

    public static final class Aggregate {
        private final int fileCount;
        private final long totalSize;
        private final long totalLines;

        public Aggregate(int fileCount, long totalSize, long totalLines) {
            this.fileCount = fileCount;
            this.totalSize = totalSize;
            this.totalLines = totalLines;
        }

        public int getFileCount() {
            return fileCount;
        }

        public long getTotalSize() {
            return totalSize;
        }

        public long getTotalLines() {
            return totalLines;
        }
    }
}

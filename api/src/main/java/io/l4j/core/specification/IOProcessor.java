package io.l4j.core.specification;

import io.l4j.core.Processor;

public interface IOProcessor<D extends DataFormat> extends Processor<ProcessorType> {

    byte[] read(String path);

    void write(String filePath, byte[] data);

}

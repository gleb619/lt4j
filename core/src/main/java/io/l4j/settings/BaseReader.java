package io.l4j.settings;

import io.l4j.core.ComponentType;
import io.l4j.core.Context;
import io.l4j.core.Converter;
import io.l4j.core.Reader;
import io.l4j.core.specification.DataFormat;
import io.l4j.core.specification.IOProcessor;
import io.l4j.core.specification.ProcessorType;
import io.l4j.model.Project;
import org.apache.commons.io.FilenameUtils;

public class BaseReader implements Reader {

    @Override
    public void onCreate(Context context) {
        String location = context.getState().getConfigFileLocation();
        IOProcessor ioProcessor = context.getComponent(ComponentType.PROCESSOR, ProcessorType.IO);
        String extension = FilenameUtils.getExtension(location);
        Converter<DataFormat> converter = context.getComponent(ComponentType.CONVERTER, DataFormat.parseType(extension));
        byte[] bytes = ioProcessor.read(location);
        Project project = converter.from(bytes, Project.class);
        context.getState().setProject(project);
    }

}

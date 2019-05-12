package com.scwvg.sys.content.thymeleaf;

import com.scwvg.sys.content.thymeleaf.tag.ScwvgDselect;
import com.scwvg.sys.content.service.TDictionaryService;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月23日15点26分
 * @Description: TODO  自定义 thymeleaf 标签方言
 * version 0.1
 */
@Component
public class ScwvgDialect extends AbstractProcessorDialect {
    // 定义方言名称
    private static final String DIALECT_NAME = "Scwvg Dialect";
    // 定义方言前缀
    private static final String DIALECT_PREFIX = "scwvg";
    // 定义优先级，注意：优先级要比1000大，100是 thymeleaf 默认的
    public static final int PRECEDENCE = 10000;

    @Resource
    private TDictionaryService tDictionaryService ;

    public ScwvgDialect() {
        super(DIALECT_NAME, DIALECT_PREFIX, PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors=new HashSet<IProcessor>();
        processors.add(new ScwvgDselect(DIALECT_PREFIX , tDictionaryService));
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }

}

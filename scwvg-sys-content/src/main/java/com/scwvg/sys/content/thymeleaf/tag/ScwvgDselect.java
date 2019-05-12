package com.scwvg.sys.content.thymeleaf.tag;

import com.scwvg.sys.content.thymeleaf.ScwvgDialect;
import com.scwvg.sys.content.entitys.TDictionary;
import com.scwvg.sys.content.service.TDictionaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;
import java.util.HashMap;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年4月23日15点26分
 * @Description: TODO  业务字典下拉框选择标签
 * version 0.1
 */
public class ScwvgDselect extends AbstractElementTagProcessor implements ScwvgTagProcessor{

    private static final String ELEMENT_NAME  = "dselect";
    private TDictionaryService tDictionaryService ;

    /**
     *
     * @param templateMode          模板模式，这里使用HTML模板
     * @param dialectPrefix         标签前缀,即xxx:dselect 中的 xxx,
     * @param elementName           匹配标签元素名, 举例来说如果是div，则我们的自定义标签只能用在div标签中。为null能够匹配所有的标签
     * @param prefixElementName     标签名是否要求前缀
     * @param attributeName         自定义标签属性名
     * @param prefixAttributeName   属性名是否要求前缀，如果为true
     * @param precedence            标签处理的优先级，此处使用和Thymeleaf标准方言相同的优先级
     */

    public ScwvgDselect(String dialectPrefix , TDictionaryService tDictionaryService) {
        super(TemplateMode.HTML,
                dialectPrefix,
                ELEMENT_NAME,
                true,
                null,
                false,
                ScwvgDialect.PRECEDENCE);
        this.tDictionaryService = tDictionaryService;
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {
        // 1. 获取应用上下文
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(context);
        // 2. 从标签中获取属性，标签中的可选属性将告诉我们需要什么样的数据
        String dselect = getOption(tag);
        // 3. 创建将替换自定义标签的HTML 的 DOM结构
        IModelFactory modelFactory = context.getModelFactory();
        IModel model = modelFactory.createModel();
        model.add(modelFactory.createText(dselect));
        // 4. 指示引擎用指定的模型替换整个元素
        structureHandler.replaceWith(model, false);
    }

    /**
     * @Description:  查询数据字典信息
     * @Param: [tag]
     * @Author: chen.baihoo
     * @Date: 2019/1/19
     */
    private String getOption(IProcessableElementTag tag) {

        //名称
        String name = tag.getAttributeValue("name");

        //数据字典的code
        String dictTypeCode=tag.getAttributeValue("dictTypeCode");

        //是否可以搜索
        String isTemp  = tag.getAttributeValue("isSearch");
        boolean isSearch = (isTemp!=null &&
                ("true".equals(isTemp.trim()) || "false".equals(isTemp.trim())))?
                Boolean.parseBoolean(isTemp) : false;

        //需要排除的编号
        String excludeId=tag.getAttributeValue("excludeId");

        //数据字典项
        String dictId=tag.getAttributeValue("dictId");

        //父数据字典项
        String pDictId=tag.getAttributeValue("pDictId");

        //父数据字典的类型
        String pDictTypeCode=tag.getAttributeValue("pDictTypeCode");

        //选中的数据字典项
        String selectDictId=tag.getAttributeValue("selectDictId");

        //默认显示的请选择的文字
        String defaultSel=tag.getAttributeValue("defaultSel");

        //是否显示请选择的文字
        isTemp  = tag.getAttributeValue("isShowDefaultSel");
        boolean isShowDefaultSel = (isTemp!=null &&
                ("true".equals(isTemp.trim()) || "false".equals(isTemp.trim())))?
                Boolean.parseBoolean(isTemp) : false;

        //是否禁用和启用
        isTemp = tag.getAttributeValue("isDisable");
        boolean isDisable = (isTemp!=null &&
                ("true".equals(isTemp.trim()) || "false".equals(isTemp.trim())))?
                Boolean.parseBoolean(isTemp) : false;

        // 过滤需要被过滤的
        String layFilter=tag.getAttributeValue("layFilter");

        //layui 修饰元素 lay-Verify 属性，检验提示内容
        String layVerify=tag.getAttributeValue("layVerify");

        // 是否多选
        isTemp = tag.getAttributeValue("isMultiple");
        boolean isMultiple = (isTemp!=null &&
                ("true".equals(isTemp.trim()) || "false".equals(isTemp.trim())))?
                Boolean.parseBoolean(isTemp) : false;

        // 定义 select 标签下显示 option 元素，例：useAttr='1,2,3,4' 。数据库查询这四个
        String useAttr=tag.getAttributeValue("useAttr");

        if (StringUtils.isNotBlank(name)
                && StringUtils.isNotBlank(dictTypeCode)) {
            // String selectDictIds;
            boolean isselect = false;
            if (StringUtils.isNotBlank(selectDictId)) {
                isselect = true;
                if (!selectDictId.startsWith(",")) {
                    selectDictId = "," + selectDictId;
                }
                if (!selectDictId.endsWith(",")) {
                    selectDictId = selectDictId + ",";
                }
                // selectDictIds=selectDictId.split(",");
            }
            HashMap<String, Object> para = new HashMap<String, Object>();
            para.put("dictTypeCode", dictTypeCode.trim());
            if (StringUtils.isNotBlank(excludeId)) {
                para.put("excludeId", excludeId.trim());
            }
            if (StringUtils.isNotBlank(dictId)) {
                para.put("dictId", dictId.trim());
            }
            if (StringUtils.isNotBlank(pDictId)) {
                para.put("pDictId", pDictId.trim());
            }
            if (StringUtils.isNotBlank(pDictTypeCode)) {
                para.put("pDictTypeCode", pDictTypeCode.trim());
            }
            TDictionary[] dicts = tDictionaryService.findTDictionary(para);
            StringBuffer select_ = new StringBuffer();
            if (StringUtils.isBlank(layVerify)) {
                layVerify = "";
            }
            select_.append("<select name=\"" + name + "\" lay-verify=\""
                    + layVerify + "\" ");
            if (isMultiple) {
                select_.append(" multiple=\"multiple\" ");
            }
            if (StringUtils.isNotBlank(useAttr)) {
                String[] useAttrs_ = useAttr.split(",");
                for (int i = 0; i < useAttrs_.length; i++) {
                    select_.append(" " + useAttrs_[i] + " ");
                }
            }
            if (isDisable) {

                select_.append(" disabled ");
            }
            if (isSearch) {
                select_.append(" lay-search ");
            }
            if (StringUtils.isNotBlank(layFilter)) {
                select_.append(" lay-filter=\"" + layFilter + "\" ");
            }
            select_.append(">");
            if (isShowDefaultSel) {
                if (StringUtils.isNotBlank(defaultSel)) {
                    select_.append("<option value>" + defaultSel + "</option>");
                } else {
                    select_.append("<option value>请选择</option>");
                }
            }
            if (dicts != null && dicts.length > 0) {
                for (TDictionary dict_ : dicts) {
                    select_.append("<option value=\"" + dict_.getDictId()
                            + "\"");
                    if (isselect
                            && selectDictId.contains("," + dict_.getDictId()
                            + ",")) {
                        select_.append(" selected ");
                    }
                    if (StringUtils.isNotBlank(dict_.getPdictId())) {
                        select_.append(" pid=\"" + dict_.getPdictId() + "\" ");
                    }
                    select_.append(">" + dict_.getDictName() + "</option>");
                }
            }
            select_.append("</select>");
            return select_.toString();
        }
        return "";
    }
}

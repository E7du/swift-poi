package cn.zhucongqi.excel.read.event;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import cn.zhucongqi.excel.kit.TypeKit;
import cn.zhucongqi.excel.metadata.Column;
import cn.zhucongqi.excel.metadata.Header;
import cn.zhucongqi.excel.read.context.AnalysisContext;
import cn.zhucongqi.excel.write.exception.GenerateException;

/**
 * 监听POI Sax解析的每行结果
 *
 * @author Jobsz
 */
public class ClassReflectBuildListener extends AnalysisEventListener<Object> {

    @SuppressWarnings("unchecked")
	@Override
    public void invoke(Object object, AnalysisContext context) {
        if(context.getHeader() != null && context.getHeader().getHeaderClazz()!=null ){
            Object resultModel = buildUserModel(context, (List<String>)object);
            context.setCurrentRowAnalysisResult(resultModel);
        }
    }

    private Object buildUserModel(AnalysisContext context, List<String> stringList) {
        Header header = context.getHeader();
        Object resultModel;
        try {
            resultModel = header.getHeaderClazz().newInstance();
        } catch (Exception e) {
            throw new GenerateException(e);
        }
        if (header != null) {
            for (int i = 0; i < stringList.size(); i++) {
                Column column = header.getHeaderColumnByIdx1(i);
                if (column != null) {
                    Object value = TypeKit.convert(stringList.get(i), column.getField(),
                    		column.getDateFormat(),context.use1904WindowDate());
                    if (value != null) {
                        try {
                            BeanUtils.setProperty(resultModel, column.getField().getName(), value);
                        } catch (Exception e) {
                            throw new GenerateException(
                            		column.getField().getName() + " can not set value " + value, e);
                        }
                    }
                }
            }
        }
        return resultModel;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}

package cn.itcast.common.util.makehtml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created by IntelliJ IDEA.
 * User: jac
 * Date: 11-10-9
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
public class FreeMakerUtil {
    private static Configuration cfg = new Configuration();

    static {
        try {
            cfg.setClassForTemplateLoading(FreeMakerUtil.class,"/resources/ftl");
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            cfg.setDefaultEncoding("UTF-8");
        } catch (Exception e) {

        }
    }

    private static Configuration getCfg() {
        return cfg;
    }

    /**
     * 生成模板
     *
     * @param root
     * @param path
     * @param ftlName
     * @throws IOException
     * @throws TemplateException
     */
    public static void outHtml(Map root, String path, String ftlName) throws IOException, TemplateException {
        Template template = getCfg().getTemplate(ftlName);
        template.setEncoding("UTF-8");
        Writer out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
        template.process(root, out);
        out.flush();
    }



}

package org.beetl.sql;

import org.beetl.sql.core.*;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2019/6/26 下午4:54
 */
public class GenPojo {

    @Test
    public void gen() {
        ConnectionSource cs = ConnectionSourceHelper.getSimple("com.mysql.jdbc.Driver", "jdbc:mysql://47.99.173.26:11001/liuyiim?verifyServerCertificate" +
                "=false&useSSL=false&requireSSL=false", "liuyiim", "@#$%%^&tdtw3224^H");
        SQLLoader loader = new ClasspathLoader("/sql");
        //SQLManager sqlManager = new SQLManager(new MySqlStyle(),loader,cs,new TUnderlinedNameConversion(), new Interceptor[]{new DebugInterceptor()});
        SQLManager sqlManager = new SQLManager(new MySqlStyle(), loader, cs, new UnderlinedNameConversion(), new Interceptor[]{new DebugInterceptor()});
        //sql.genPojoCodeToConsole("userRole"); 快速生成，显示到控制台

        // 或者直接生成java文件
        GenConfig config = new GenConfig("entity.btl");
        config.setBaseClass("AbstractEntity");
        config.setOutputPackage("com.creolophus.im.common.entity");
        config.preferBigDecimal(true);
        try {
            sqlManager.genPojoCode("tomato_group", config.getOutputPackage(), config);
            sqlManager.genPojoCode("tomato_friend", config.getOutputPackage(), config);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

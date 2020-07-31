import javax.swing.JFrame;

import java.awt.Font;
import javax.swing.*;

/**
 * @author magicnana
 * @date 2020/7/30 下午3:38
 */
public class MainFrame extends JFrame {
    JLayeredPane jlp;
    JPanel jp;
    JPanel jp2;
    JLabel jl;
    JLabel jl2;
    ImageIcon img;
    JTextField jtf1;
    RoundButton button;


    public MainFrame() {
        // TODO Auto-generated constructor stub

        //初始化层级面板jlp
        jlp = new JLayeredPane();
        img = new ImageIcon("img/主界面.jpg");
        //初始化面板jp
        jp = new JPanel();
        jp.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
        //添加背景图片到面板jp中
        jl = new JLabel(img);
        jp.add(jl);

        //设置标签“消费总计”
        jl2 = new JLabel("消费总计：");
        jl2.setFont(new Font("Dialog",1,55));
        jl2.setBounds(50,50,300,100);

        //设置消费总计单行文本框
        jtf1 = new JTextField();
        jtf1.setBounds(50,180,400,110);
        jtf1.setFont(new Font("Dialog",1,55));


        //添加确认按钮
        button = new RoundButton();
        button.setText("确认");
        button.setFont(new Font("Dialog",1,55));
        button.setBounds(250,400,200,80);



        //初始化超市消费信息消费面板jp2，设置布局为空
        jp2 = new JPanel();
        jp2.setLayout(null);

        //为jp2添加控件按钮，输入文本框，标签
        jp2.add(jl2);
        jp2.add(jtf1);
        jp2.add(button);
        jp2.setBounds(700, 180, 500, 600);
        //将jp添加到底层容器中
        jlp.add(jp,JLayeredPane.DEFAULT_LAYER);
        //将jp2添加到上层容器中
        jlp.add(jp2,JLayeredPane.MODAL_LAYER);


        //去掉标题栏
        this.setUndecorated(true);

        this.setLayeredPane(jlp);
        this.setSize(img.getIconWidth(),img.getIconHeight());
        this.setLocation(0,0);
        this.setVisible(true);

    }
}

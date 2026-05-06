import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class RenderCommercialLoopJpg {
    static final int W = 1920;
    static final int H = 1080;

    public static void main(String[] args) throws Exception {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        paintBackground(g);
        paintTitle(g);
        paintTopFlow(g);
        paintCenter(g);
        paintDestinations(g);
        paintFooter(g);

        g.dispose();
        File out = new File("/workspaces/AIStudy/outputs/final/单页PPT_驿站消费问卦引流商业逻辑_20260506.jpg");
        ImageIO.write(img, "jpg", out);
    }

    static Font font(int style, int size) {
        return new Font("Microsoft YaHei", style, size);
    }

    static void paintBackground(Graphics2D g) {
        GradientPaint bg = new GradientPaint(0, 0, new Color(7, 21, 34), W, H, new Color(6, 17, 29));
        g.setPaint(bg);
        g.fillRect(0, 0, W, H);

        RadialGradientPaint glow = new RadialGradientPaint(
                new Point2D.Float(960, 500), 760,
                new float[]{0f, 0.52f, 1f},
                new Color[]{new Color(74, 215, 255, 78), new Color(32, 112, 155, 38), new Color(8, 20, 32, 0)}
        );
        g.setPaint(glow);
        g.fillRect(0, 0, W, H);

        g.setStroke(new BasicStroke(72, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(255, 255, 255, 15));
        Path2D p = new Path2D.Double();
        p.moveTo(-40, 870);
        p.curveTo(270, 730, 500, 940, 775, 780);
        p.curveTo(1000, 640, 1260, 660, 1980, 785);
        g.draw(p);
        g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(125, 232, 255, 190));
        g.draw(p);

        g.setStroke(new BasicStroke(58, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(255, 255, 255, 12));
        Path2D p2 = new Path2D.Double();
        p2.moveTo(-30, 238);
        p2.curveTo(260, 128, 500, 280, 820, 190);
        p2.curveTo(1110, 110, 1320, 118, 1970, 230);
        g.draw(p2);
        g.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(99, 223, 255, 110));
        g.draw(p2);
    }

    static void paintTitle(Graphics2D g) {
        g.setFont(font(Font.BOLD, 20));
        g.setColor(new Color(127, 232, 255));
        g.drawString("COMMERCIAL LOOP DESIGN", 92, 86);
        g.setFont(font(Font.BOLD, 52));
        g.setColor(Color.WHITE);
        g.drawString("驿站消费 · AI问卦 · 下一站引流商业逻辑", 90, 150);
        g.setFont(font(Font.PLAIN, 24));
        g.setColor(new Color(201, 244, 255));
        g.drawString("把每个节点从“单次打卡点”变成“消费触发点、问题输入点、游线分发点”", 92, 194);
    }

    static void card(Graphics2D g, int x, int y, int w, int h, String num, String title, String a, String b) {
        shadow(g, x, y, w, h, 18);
        RoundRectangle2D rr = new RoundRectangle2D.Double(x, y, w, h, 18, 18);
        g.setPaint(new GradientPaint(x, y, Color.WHITE, x + w, y + h, new Color(224, 245, 255)));
        g.fill(rr);
        g.setColor(new Color(183, 239, 255));
        g.setStroke(new BasicStroke(2));
        g.draw(rr);

        RoundRectangle2D icon = new RoundRectangle2D.Double(x + 30, y + 30, 72, 72, 20, 20);
        g.setColor(new Color(15, 143, 193));
        g.fill(icon);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString(num, x + 55, y + 79);

        g.setFont(font(Font.BOLD, 28));
        g.setColor(new Color(17, 50, 74));
        g.drawString(title, x + 123, y + 54);
        g.setFont(font(Font.PLAIN, 20));
        g.setColor(new Color(53, 91, 112));
        g.drawString(a, x + 123, y + 90);
        g.drawString(b, x + 123, y + 120);
    }

    static void paintTopFlow(Graphics2D g) {
        card(g, 92, 246, 350, 160, "1", "入口种草", "坎门打卡 / 扫码入场", "建立主题期待");
        card(g, 590, 246, 350, 160, "2", "首站消费", "咖啡 / 文创 / 限定券", "消费后触发问卦");
        card(g, 1088, 246, 350, 160, "3", "AI问卦", "输入人生问题", "生成智慧签和权益");
        arrow(g, 442, 326, 580, 326);
        arrow(g, 940, 326, 1078, 326);
    }

    static void paintCenter(Graphics2D g) {
        shadow(g, 487, 480, 440, 210, 26);
        RoundRectangle2D cafe = new RoundRectangle2D.Double(487, 480, 440, 210, 26, 26);
        g.setPaint(new GradientPaint(487, 480, new Color(29, 109, 149), 927, 690, new Color(15, 52, 78)));
        g.fill(cafe);
        g.setColor(new Color(120, 230, 255));
        g.setStroke(new BasicStroke(3));
        g.draw(cafe);
        g.setFont(font(Font.BOLD, 27));
        g.setColor(Color.WHITE);
        g.drawString("AI问卦咖啡店", 530, 540);
        g.setFont(font(Font.PLAIN, 20));
        g.setColor(new Color(217, 248, 255));
        g.drawString("小型咖啡吧 + 文创售卖 + 互动屏", 530, 584);
        g.drawString("游客在消费场景里完成问卦", 530, 618);
        pill(g, 530, 642, 118, "首站");
        pill(g, 662, 642, 118, "核销");
        pill(g, 794, 642, 92, "会员");

        shadow(g, 1050, 457, 256, 256, 128);
        g.setColor(new Color(16, 54, 80));
        g.fillOval(1050, 457, 256, 256);
        g.setColor(new Color(142, 238, 255));
        g.setStroke(new BasicStroke(4));
        g.drawOval(1050, 457, 256, 256);
        g.setColor(new Color(255, 255, 255, 65));
        g.setStroke(new BasicStroke(2));
        g.drawOval(1080, 487, 196, 196);
        g.setFont(font(Font.BOLD, 34));
        g.setColor(Color.WHITE);
        g.drawString("智慧签", 1110, 562);
        g.setFont(font(Font.PLAIN, 21));
        g.setColor(new Color(217, 248, 255));
        g.drawString("卦象启发", 1083, 604);
        g.drawString("行动建议", 1083, 636);
        g.drawString("下一站优惠", 1083, 668);

        cardSmall(g, 1298, 498, 250, 116, "分流推荐", "按问题类型给出理由");
        arrow(g, 1260, 406, 1204, 528);
        arrow(g, 1070, 585, 934, 585);
        arrow(g, 1306, 585, 1298, 565);
    }

    static void paintDestinations(Graphics2D g) {
        destination(g, 180, 792, "水镜中庭", "情绪 / 健康 / 休憩", "轻饮、摄影、停留");
        destination(g, 650, 792, "突破光环", "事业 / 转型 / 打卡", "纪念照、徽章、周边");
        destination(g, 1120, 792, "潮汐市集", "社交 / 消费 / 快闪", "饮品、手作、零售");
        destination(g, 1488, 792, "文创零售站", "收藏 / 伴手礼 / 集章", "卡牌、杯具、小书");
        arrowCurve(g, 1423, 614, 1320, 776, 1195, 796);
        arrowCurve(g, 1423, 614, 1535, 760, 1510, 792);
        arrowCurve(g, 1423, 614, 1015, 710, 890, 790);
        arrowCurve(g, 1423, 614, 595, 704, 370, 790);
    }

    static void paintFooter(Graphics2D g) {
        RoundRectangle2D footer = new RoundRectangle2D.Double(90, 972, 1740, 62, 31, 31);
        g.setColor(new Color(14, 43, 64));
        g.fill(footer);
        g.setColor(new Color(85, 223, 255, 165));
        g.setStroke(new BasicStroke(2));
        g.draw(footer);
        g.setFont(font(Font.PLAIN, 18));
        g.setColor(new Color(189, 239, 255));
        g.drawString("运营结果：消费核销数据、问题偏好标签、停留时长、会员积分、智慧签分享，持续推动二次到访和复购", 130, 1012);
    }

    static void pill(Graphics2D g, int x, int y, int w, String text) {
        RoundRectangle2D rr = new RoundRectangle2D.Double(x, y, w, 32, 16, 16);
        g.setColor(new Color(191, 246, 255));
        g.fill(rr);
        g.setFont(font(Font.BOLD, 18));
        g.setColor(new Color(7, 48, 68));
        g.drawString(text, x + 28, y + 23);
    }

    static void cardSmall(Graphics2D g, int x, int y, int w, int h, String title, String text) {
        shadow(g, x, y, w, h, 18);
        RoundRectangle2D rr = new RoundRectangle2D.Double(x, y, w, h, 18, 18);
        g.setColor(new Color(248, 253, 255));
        g.fill(rr);
        g.setColor(new Color(183, 239, 255));
        g.setStroke(new BasicStroke(2));
        g.draw(rr);
        g.setFont(font(Font.BOLD, 28));
        g.setColor(new Color(17, 50, 74));
        g.drawString(title, x + 32, y + 47);
        g.setFont(font(Font.PLAIN, 20));
        g.setColor(new Color(53, 91, 112));
        g.drawString(text, x + 32, y + 82);
    }

    static void destination(Graphics2D g, int x, int y, String title, String a, String b) {
        RoundRectangle2D rr = new RoundRectangle2D.Double(x, y, 300, 130, 18, 18);
        g.setColor(new Color(18, 53, 78));
        g.fill(rr);
        g.setColor(new Color(102, 221, 255));
        g.setStroke(new BasicStroke(2));
        g.draw(rr);
        g.setFont(font(Font.BOLD, 23));
        g.setColor(Color.WHITE);
        g.drawString(title, x + 34, y + 48);
        g.setFont(font(Font.PLAIN, 18));
        g.setColor(new Color(215, 248, 255));
        g.drawString(a, x + 34, y + 86);
        g.drawString(b, x + 34, y + 114);
    }

    static void arrow(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.setColor(new Color(158, 240, 255));
        g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(x1, y1, x2, y2);
        drawArrowHead(g, x1, y1, x2, y2);
    }

    static void arrowCurve(Graphics2D g, int x1, int y1, int cx, int cy, int x2, int y2) {
        QuadCurve2D q = new QuadCurve2D.Double(x1, y1, cx, cy, x2, y2);
        g.setColor(new Color(158, 240, 255));
        g.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(q);
        drawArrowHead(g, cx, cy, x2, y2);
    }

    static void drawArrowHead(Graphics2D g, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int len = 18;
        Path2D head = new Path2D.Double();
        head.moveTo(x2, y2);
        head.lineTo(x2 - len * Math.cos(angle - Math.PI / 6), y2 - len * Math.sin(angle - Math.PI / 6));
        head.lineTo(x2 - len * Math.cos(angle + Math.PI / 6), y2 - len * Math.sin(angle + Math.PI / 6));
        head.closePath();
        g.fill(head);
    }

    static void shadow(Graphics2D g, int x, int y, int w, int h, int arc) {
        g.setColor(new Color(0, 8, 14, 70));
        g.fill(new RoundRectangle2D.Double(x, y + 14, w, h, arc, arc));
    }
}

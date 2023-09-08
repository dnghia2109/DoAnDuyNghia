package vn.techmaster.finalproject.service.serviceimplement;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.service.serviceinterface.HTMLService;

@Component
public class HTMLServiceImpl implements HTMLService {
    @Override
    public String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}

package codenet.utils;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TableBlock;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by oc on 2017/9/5.
 */
public class Utils {

    public static String getFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }



    public static String markdownToHtml(String markdown) {
        MutableDataSet options=new MutableDataSet();
        options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");
        options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create(),
                new HtmlRenderer.HtmlRendererExtension() {
                    @Override
                    public void rendererOptions(MutableDataHolder mutableDataHolder) {

                    }

                    @Override
                    public void extend(HtmlRenderer.Builder builder, String s) {
                        builder.attributeProviderFactory(new IndependentAttributeProviderFactory() {
                            @Override
                            public AttributeProvider create(NodeRendererContext nodeRendererContext) {
                                return (node, attributablePart, attributes) -> {
                                    // Add attributes here
                                    if (node instanceof TableBlock) {
                                        attributes.addValue("class", "table table-hover table-striped");
                                        attributes.addValue("style", "width: auto");
                                    }
                                };
                            }
                        });
                    }
                }));
        Parser parser=Parser.builder(options).build();
        HtmlRenderer renderer=HtmlRenderer.builder(options).build();
        Node document=parser.parse(markdown);
        return renderer.render(document);
    }

}

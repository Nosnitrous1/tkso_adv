import com.google.common.math.Stats;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import org.openqa.selenium.* ;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import java.io.* ;

public class tkso {
    public static int Statis = 0;
    public static boolean first_not_two = true;
    public static void main(String[] args) {
        // 0. a file to write a log
        String FilePath = "log.txt";

        // 1.	Открыть главную страницу tkso.ru
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.tkso.ru");
        int NoP = 1;            // Depth of blog pages

        for (int ii = 1; ii <= NoP; ii++) {
            List<WebElement> Element = driver.findElements(By.className("entry-title"));
            int Fin = Element.size();
            AddToLog(FilePath, "Number of Elements " + Fin);

            int j = 0;
            String Links;
            String[] Curhref = new String[Fin];
            String[] Curtext = new String[Fin];
            for (WebElement e : Element) {
                Curtext[j] = e.getText();

                WebElement TxtLink2 = driver.findElement(By.linkText(Curtext[j]));
                Curhref[j] = TxtLink2.getAttribute("href");
                System.out.println(" Curhref[" + j + "]: " + Curhref[j] + " " + java.time.LocalDateTime.now() + "\n");
                j++;
            }
            AddToLog(FilePath, "Start of a page content loop ");
            for (int i = 0; i < Fin; i++) {
                AddToLog(FilePath, " Call of page # " + i + ": " + Curhref[i]);
                driver.get(Curhref[i]);
                // TxtLink.click();
                driver.navigate().refresh();
                driver.navigate().refresh();
                driver.navigate().refresh();
                driver.navigate().back();
                Y_tInNewTab(FilePath, "tkso.ru",  Curtext[i]);
            }
            // The next page
            WebElement Paginate = driver.findElement(By.linkText("Вперёд"));
            AddToLog(FilePath, " Next page " + ii + "  Вперёд");
            Paginate.click();
        }
    }

    // добавляем новое окно и открываем в нём yandex.ru
    private static void Y_tInNewTab(String FilePath, String MySite, String text) {
        WebDriver driver2 = new FirefoxDriver();
        driver2.get("https://www.yandex.ru");
        driver2.manage().window().maximize();
//        driver2.manage().window().setSize(new Dimension(0,0));



        String parent_window = driver2.getWindowHandle();
//<input class="input__control input__input" tabindex="2" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" aria-autocomplete="list" aria-label="Запрос" id="text" maxlength="400" autofocus="" name="text">
        WebElement Serchtxt = driver2.findElement(By.name("text"));
        Serchtxt.clear();
        Serchtxt.sendKeys(text);
//<span class="button__text">Найти</span>
        WebElement SearchBtn = driver2.findElement(By.tagName("button"));
        SearchBtn.click();

        int NoP = 5;
        for (int ii = 1; ii <= NoP; ii++) {
            //<li class="serp-item" data-cid="0" data-log-node="gbtu" data-first-snippet=""><div class="organic typo typo_text_m typo_line_s"><h2 class="organic__title-wrapper typo typo_text_l typo_line_m"><div class="a11y-hidden">1</div><a class="link link_theme_normal organic__url link_cropped_no i-bem" data-bem="{&quot;link&quot;:{}}" rel="noopener" accesskey="1" tabindex="2" data-log-node="gbtv" target="_blank" href="http://tkso.ru/" data-counter="[&quot;rc&quot;,&quot;http://yandex.ru/clck/jsredir?from=yandex.ru%3Bsearch%2F%3Bweb%3B%3B&amp;text=&amp;etext=2199.opV3D8MIty1dXRwFf71t30u6_MzN3NY2HQJpJsPJWxkaN4r4UZTjHbbqGAcCL6cM6WoeH_uSAVPIly8pTjFhU494lHs_Y7DWLJPSWK1RZQ0.2e8dad8b34f5d8b46e3a76e3dd5a5db9d07323df&amp;uuid=&amp;state=PEtFfuTeVD5kpHnK9lio9dFa2ePbDzX7kPpTCH_rtQkH2bBEi5M--bO-cYhaTVRUPt9FXYN03weBS9nKEr_LVd0b6HOMUidQ&amp;&amp;cst=AiuY0DBWFJ5fN_r-AEszk2K1TsI9Paz205F03NB_ePoGW8_-rs4dvaU8xXnJqTExAjVoHyfem4f8IUYzKXXVSPOpNVP1EdLAnwjOxi2w6zBLEQBJI4GOKmDfYdPKNubZONihaDdDIN1FReZfb1_nKYGncZSoFMN1BmLGl8sYg3au_zlayVxNazc_Y1Ry2Rom_yyuP6xiX26K4s6rvH8eVUQPHHlSrhg3V7xWVQz8qOR3CMTAcFJ0VTpp-tIoYL2j9fBaLKZkr3S9qM2uIBmwWygMrRsmF5K4Y5sIW24GKboIzWF_8yO9aG5DZY3Rtr3PLmclOOOs1IREzbyi6cuSfscsWK4p7-hUQ4CR0oSonBm-4hzsWWaaE55AulVs0RG9TanQ_vh5f_RsSEhN41JTD2ZNCwK4vIb9XF4DZYr9XrkPsHPQ88U9WfrXJS0LGt94jCWzpyIV1kMz63u_-X-Bt2Q-oPFVFmE3tWDjYEEyc9uTgyCpbvGC9yLZJPtuoUClKBlXBe6EmDr2rmNHCVJ_thgPXAhiBGfmnbCbb6vj1GHvjmHf8z9rJ3eJWXjIz-eLJ3zSk7ceoRQhspBC-akwEIXi2zrqfAzevPcey7-Hh3GzKJmK19C6TC9v-gnx4A5h5a5A_00Aj-wg_AqkRPHNkU6eNq7AFKv75CX4d348EcWUTK7z8EzV7yVb08ynToS5nEuscVcPWtkIxg9Lb29KkjIp1tIKNxkwFkNLRPkBoSbaGLL0shzD3lNhfdGTcf3PB9gyNwhwflm7mXM_Yzn_kukELLHoVyt8I5KmMZrgJs4CS_mjKtcaaNsu9O5WSdAFcdJsZF4rWXss1pExZr3txg,,&amp;data=UlNrNmk5WktYejR0eWJFYk1LdmtxcGJmbGpVMFNpTFludVNacGh6U3BLX18zODJvaFFOUDlfWFUzMWhvcGlSRmQ3cHV2VG5GelBnZnk2TFdtT1NNemlpUDJrWnpFQjhj&amp;sign=f4eecfed2d0a9a8f3e20d786ee3ebde8&amp;keyno=8&amp;b64e=2&amp;ref=orjY4mGPRjk5boDnW0uvlrrd71vZw9kp5uQozpMtKCWbgDHRNa-0yhDPDUaIIVCoGpYOmwitFexI1o_YUua4NzXSuZ0BAbBJInaW_VXYuhr8XsbXWL8TJNflgmq0GmGgo5kUdC77mx7inmj_zt4e_eSQb8ZWCTICiJTyeRR5kvSq-EJq5qNP83862CHoRfqwlSsujvE1B6mh1yNwNB9gih50skgwYBrK1UA1e3h1G0-C0EMrOyC4vBLDZo22Odwk_ZSb_K9BwQfKACbEdOeSZChoKIgAqQrh6Nx31Q1T-qzxLUTfDdx7m5QLClV5DsWD&amp;l10n=ru&amp;rp=1&quot;]"><div class="favicon favicon_page_0"><div class="favicon__icon" data-rcid="81" style="background-position:0 0px;"></div></div><div class="organic__url-text">ТКСО. <b class="needsclick">Туризм</b>, <b class="needsclick">Культура</b>, <b class="needsclick">Спорт</b> и <b class="needsclick">Общество</b> - Главная</div></a></h2><div class="organic__subtitle typo typo_type_greenurl"><div class="path organic__path" data-log-node="gbtw"><a class="link link_theme_outer path__item i-bem link_js_inited" data-bem="{&quot;link&quot;:{}}" rel="noopener" data-log-node="gbtx" tabindex="-1" target="_blank" href="http://tkso.ru/" data-counter="[&quot;rc&quot;,&quot;http://yandex.ru/clck/jsredir?from=yandex.ru%3Bsearch%2F%3Bweb%3B%3B&amp;text=&amp;etext=2199.opV3D8MIty1dXRwFf71t30u6_MzN3NY2HQJpJsPJWxkaN4r4UZTjHbbqGAcCL6cM6WoeH_uSAVPIly8pTjFhU494lHs_Y7DWLJPSWK1RZQ0.2e8dad8b34f5d8b46e3a76e3dd5a5db9d07323df&amp;uuid=&amp;state=PEtFfuTeVD4jaxywoSUvtB2i7c0_vxGd2E9eR729KuIQGpPxcKWQSHSdfi63Is_-FTQakDLX4Cm898924SG_gw3_Ej3CZklP&amp;&amp;cst=AiuY0DBWFJ5fN_r-AEszk2K1TsI9Paz205F03NB_ePoGW8_-rs4dvaU8xXnJqTExAjVoHyfem4f8IUYzKXXVSPOpNVP1EdLAnwjOxi2w6zBLEQBJI4GOKmDfYdPKNubZONihaDdDIN1FReZfb1_nKYGncZSoFMN1BmLGl8sYg3au_zlayVxNazc_Y1Ry2Rom_yyuP6xiX26K4s6rvH8eVUQPHHlSrhg3V7xWVQz8qOR3CMTAcFJ0VTpp-tIoYL2j9fBaLKZkr3S9qM2uIBmwWygMrRsmF5K4Y5sIW24GKboIzWF_8yO9aG5DZY3Rtr3PLmclOOOs1IREzbyi6cuSfscsWK4p7-hUQ4CR0oSonBm-4hzsWWaaE55AulVs0RG9TanQ_vh5f_RsSEhN41JTD2ZNCwK4vIb9XF4DZYr9XrkPsHPQ88U9WfrXJS0LGt94jCWzpyIV1kMz63u_-X-Bt2Q-oPFVFmE3tWDjYEEyc9uTgyCpbvGC9yLZJPtuoUClKBlXBe6EmDr2rmNHCVJ_thgPXAhiBGfmnbCbb6vj1GHvjmHf8z9rJ3eJWXjIz-eLJ3zSk7ceoRQhspBC-akwEIXi2zrqfAzevPcey7-Hh3GzKJmK19C6TC9v-gnx4A5h5a5A_00Aj-wg_AqkRPHNkU6eNq7AFKv75CX4d348EcWUTK7z8EzV7yVb08ynToS5nEuscVcPWtkIxg9Lb29KkjIp1tIKNxkwFkNLRPkBoSbaGLL0shzD3lNhfdGTcf3PB9gyNwhwflm7mXM_Yzn_kukELLHoVyt8I5KmMZrgJs4CS_mjKtcaaNsu9O5WSdAFcdJsZF4rWXss1pExZr3txg,,&amp;data=UlNrNmk5WktYejR0eWJFYk1LdmtxcGJmbGpVMFNpTFludVNacGh6U3BLX18zODJvaFFOUDlfWFUzMWhvcGlSRmQ3cHV2VG5GelBnZnk2TFdtT1NNemlpUDJrWnpFQjhj&amp;sign=daf0bef340fa17e3d96088ee2dc0c419&amp;keyno=8&amp;b64e=2&amp;ref=orjY4mGPRjk5boDnW0uvlrrd71vZw9kp5uQozpMtKCWbgDHRNa-0yhDPDUaIIVCoGpYOmwitFexI1o_YUua4NzXSuZ0BAbBJInaW_VXYuhr8XsbXWL8TJNflgmq0GmGgo5kUdC77mx7inmj_zt4e_eSQb8ZWCTICiJTyeRR5kvSq-EJq5qNP83862CHoRfqwlSsujvE1B6mh1yNwNB9gih50skgwYBrK1UA1e3h1G0-C0EMrOyC4vBLDZo22Odwk_ZSb_K9BwQfKACbEdOeSZChoKIgAqQrh6Nx31Q1T-qzxLUTfDdx7m5QLClV5DsWD&amp;l10n=ru&amp;rp=1&quot;]"><b>tkso.ru</b></a></div><div class="extralinks i-bem" data-bem="{&quot;extralinks&quot;:{}}" data-log-node="gbty"><div class="popup2 popup2_target_anchor popup2_autoclosable_yes popup2_theme_normal popup2_hiding_yes popup2_view_classic extralinks__popup i-bem popup2_js_inited" data-bem="{&quot;popup2&quot;:{&quot;directions&quot;:[&quot;bottom-center&quot;]}}"><a class="link link_theme_clear i-bem" data-bem="{&quot;link&quot;:{}}" rel="noopener" data-log-node="gbtz" target="_blank" tabindex="0" href="http://hghltd.yandex.net/yandbtm?lang=ru&amp;fmode=inject&amp;tm=1561915214&amp;tld=ru&amp;la=1561466880&amp;text=%D0%A2%D1%83%D1%80%D0%B8%D0%B7%D0%BC%20%D0%9A%D1%83%D0%BB%D1%8C%D1%82%D1%83%D1%80%D0%B0%20%D0%A1%D0%BF%D0%BE%D1%80%D1%82%20%D0%9E%D0%B1%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%BE&amp;url=http%3A%2F%2Ftkso.ru%2F&amp;l10n=ru&amp;mime=html&amp;sign=81c04ef0988fd8699ce22ad3dae8c089&amp;keyno=0" data-counter="[&quot;w&quot;,&quot;80.22.362&quot;,&quot;-adapters=extended-snippet,84=85,-source=web&quot;]">Сохранённая копия</a><span class="link link_theme_clear show-feedback i-bem" data-bem="{&quot;link&quot;:{},&quot;show-feedback&quot;:{&quot;abuseLink&quot;:&quot;https://yandex.ru/search/abuse?sign=97942205880dea707a151f4e809567b2&amp;keyno=0&quot;,&quot;reqid&quot;:&quot;1561915214661516-1265962546599825873400035-vla1-2206&quot;}}" rel="noopener" data-log-node="gbu0" target="_blank" tabindex="0" role="button" data-counter="[&quot;w&quot;,&quot;80.22.1436&quot;,&quot;689=1086,-adapters=extended-snippet,84=85,-source=web&quot;]">Пожаловаться</span><a class="link link_theme_clear i-bem" data-bem="{&quot;link&quot;:{}}" rel="noopener" data-log-node="gbu1" target="_blank" tabindex="0" href="https://webmaster.yandex.ru/siteinfo/?site=tkso.ru" data-counter="[&quot;w&quot;,&quot;80.22.3294&quot;,&quot;-adapters=extended-snippet,84=85,-source=web&quot;]">Информация о сайте</a></div></div></div><div class="organic__content-wrapper"><div class="text-container typo typo_text_m typo_line_m"><label class="extended-text" data-log-node="gbu2"><input class="extended-text__control" type="checkbox"><span class="extended-text__short">События, анализ, размышления. <b>Туризм</b>, <b>Культура</b>, <b>Спорт</b>, и <b>Общество</b>. Честный взгляд на новости.&nbsp;... <b>Туризм</b>, <b>Культура</b>, <b>Спорт</b> и <b>Общество</b>. Портал о жизни столицы, страны и мира, разные грани, срезы и аспекты. <span class="link link_theme_normal extended-text__toggle needsclick i-bem" data-bem="{&quot;link&quot;:{}}" data-log-node="gbu3" tabindex="0" role="button" data-counter="[&quot;w&quot;,&quot;80.22.75&quot;,&quot;689=1086,-adapters=extended-snippet,84=85,-source=web&quot;]">Читать ещё</span></span><span class="extended-text__full">События, анализ, размышления. <b>Туризм</b>, <b>Культура</b>, <b>Спорт</b>, и <b>Общество</b>. Честный взгляд на новости. Внутренний <b>туризм</b>. Поддержка <b>туризма</b>. События <b>культуры</b>. <b>Спортивные</b> события, матчи. События общественной жизни, политические, экономические, духовные.&nbsp;... <b>Туризм</b>, <b>Культура</b>, <b>Спорт</b> и <b>Общество</b>. Портал о жизни столицы, страны и мира, разные грани, срезы и аспекты. Туристическая отрасль в упадке? Все "за" и "против" отдыха за рубежом! Важнейшие культурные события города. <b>Культуры</b> мало не бывает! <b>Спорт</b> и физкультура объединяет миллионы. Важнейшие <b>спортивные</b> события, матчи, чемпионаты, аналитика. Общественная жизнь. Политические, экономические, духовные аспекты, научные достижения. <span class="link link_theme_normal extended-text__toggle needsclick i-bem" data-bem="{&quot;link&quot;:{}}" data-log-node="gbu4" tabindex="0" role="button" data-counter="[&quot;w&quot;,&quot;80.22.486&quot;,&quot;689=1086,-adapters=extended-snippet,84=85,-source=web&quot;]">Скрыть</span></span></label></div></div></div><script nonce="3440" data-type="force-paint"></script><style nonce="3440" data-stylesheet="progressive">.organic__verified{margin-top:-2px;margin-bottom:-2px;margin-left:4px;-webkit-flex-shrink:0;-ms-flex-negative:0;flex-shrink:0}.verified{line-height:1em}.verified,.verified-list{display:inline-block;white-space:nowrap}.verified-list__wrapper{height:20px;display:-webkit-flex;display:-ms-flexbox;display:flex;-webkit-flex-direction:row;-ms-flex-direction:row;flex-direction:row;-webkit-flex-wrap:wrap;-ms-flex-wrap:wrap;flex-wrap:wrap;overflow:hidden}.verified-list__item{margin-bottom:10px}.verified__text{margin-left:3px;display:inline-block}.verified__icon{position:relative;top:-1px;display:inline-block;vertical-align:middle;width:20px;height:20px;background-repeat:no-repeat;background-position:center;background-size:contain}.verified__icon+.verified__link_popup_yes.link{display:none}.verified__tooltip.verified__tooltip_view_default{max-width:265px}.verified__tooltip.verified__tooltip_view_default .tooltip__content{padding:10px 12px;font-size:13px}.verified__link_popup_yes.verified__link,.verified__tooltip_view_default .link_theme_normal{display:table;opacity:.6}.verified__link_popup_yes.verified__link:hover,.verified__tooltip_view_default .link_theme_normal:hover{opacity:1}.verified__link_popup_yes.verified__link,.verified__link_popup_yes.verified__link:hover,.verified__link_popup_yes.verified__link:visited,.verified__tooltip_view_default .link_theme_normal,.verified__tooltip_view_default .link_theme_normal:hover,.verified__tooltip_view_default .link_theme_normal:visited{color:#fff}.verified-list{vertical-align:bottom}.verified__tooltip.verified__tooltip_view_default .tooltip__content{line-height:16px}.verified__link_popup_yes.verified__link,.verified__tooltip_view_default .link_theme_normal{margin-top:2px}.verified__text{position:relative;z-index:-1;top:1px}.verified__icon_type_more120{background-image:url("//yastatic.net/s3/web4static/_/W_-LQC560ttBX-MCei9oML5gHJ4.svg")}.organic__subtitle_nowrap{display:-webkit-flex;display:-ms-flexbox;display:flex;white-space:nowrap;-webkit-align-items:center;-ms-flex-align:center;align-items:center}.button2_width_auto{max-width:100%}.button2_width_auto .button2__text{display:block;overflow:hidden;text-overflow:ellipsis}.button2.button2_sticker .button2__text{display:inline-block;vertical-align:top;margin-left:11px}.button2.button2_sticker.button2_size_m{height:36px}.button2__sticker{display:inline-block;overflow:hidden;text-transform:uppercase;color:#fff}.button2_size_m .button2__sticker{border-top-left-radius:4px;border-bottom-left-radius:4px}.button2__sticker svg{position:absolute;top:0;left:0}.button2__sticker,.button2__sticker b{position:relative}.button2__sticker_theme_blue{fill:#1e88e5}.button2__sticker_theme_red{fill:#e53935}.button2__sticker_theme_orange{fill:#f57c00}.button2__sticker_theme_green{fill:#43a047}.button2__sticker_theme_grey{fill:#888}.button2__sticker_size_s{font-size:10px;font-size:calc((11px) - 2px)}.button2__sticker_size_m{font-size:11px}.button2__sticker{line-height:38px;width:30px;height:100%}.button2 .button2__icon{position:relative;pointer-events:none}.button2 .button2__icon_side_left{position:absolute;top:0;left:0}.button2 .button2__icon_side_right{position:absolute;top:0;right:0}.icon{display:inline-block;background-repeat:no-repeat;background-position:50%}.icon:after{visibility:hidden;content:'\00A0'}.icon{width:16px}.icon{display:inline-block;background-repeat:no-repeat;background-position:50%}.icon:after{visibility:hidden;content:'\00A0'}.icon{width:16px}.serp-item-actions{margin:6px 0 0}.serp-item-actions__buttons{margin:0 0 -6px -10px}.serp-item-actions__buttons_with-bottom-label{margin-bottom:0}.serp-item-actions__button,.serp-item-actions__button.button2{margin:0 0 6px 10px;display:inline-block;vertical-align:top}.serp-item-actions__button_action_disk-save~.serp-item-actions__button_action_disk-open{display:none}.serp-item-actions{display:table}</style></li>
            List<WebElement> Element = driver2.findElements(By.className("serp-item"));
            int Fin = Element.size();
            System.out.println("Fin = " + Fin + "   " + java.time.LocalDateTime.now());
            int j = 0;
            String Curhref = "", Curtxt = "";
            for (WebElement e : Element) {
                Curtxt = e.getText();
                String tempArray[] = Curtxt.split("\n");
                if (tempArray[1].contains(MySite)) {
                    AddToLog(FilePath, "Ready to call tempArray[1] = " + tempArray[1] + " j= " + j + " ii= " + ii);
                    AddToLog(FilePath, tempArray[0]);
                    Statis++;
                    Actions builder = new Actions(driver2);
                    builder.moveByOffset(e.getLocation().getX() + 1, e
                            .getLocation().getY() + 1);
                    if (first_not_two) {
                        builder.contextClick().sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).perform();
                        first_not_two = false; }
                    else {
                        builder.click().perform();
                        first_not_two = true;
                    }
                    System.out.println("Waiting for 2 seconds ");
                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                    Set<String> windowHandlers = driver2.getWindowHandles();
                    int k=0;
                    for (String curhandle : windowHandlers) {
                        if (k>0) {
                            AddToLog(FilePath, "Close child window!=parent= " + curhandle+ "  parent= "+parent_window+" k=  "+k);
                            driver2.switchTo().window(curhandle).close();
                        }
                        k++;
                    }
                    driver2.switchTo().window(parent_window);
                }
            }
            //<a class="link link_theme_none link_target_serp pager__item pager__item_kind_next i-bem link_js_inited" data-bem="{&quot;link&quot;:{}}" data-log-node="g06q" tabindex="0" href="/search/?lr=213&amp;msid=1561921469.49555.158441.856945&amp;text=%D0%A2%D1%83%D1%80%D0%B8%D0%B7%D0%BC%20%D0%9A%D1%83%D0%BB%D1%8C%D1%82%D1%83%D1%80%D0%B0%20%D0%A1%D0%BF%D0%BE%D1%80%D1%82%20%D0%9E%D0%B1%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%BE&amp;p=1" data-counter="[&quot;w&quot;,&quot;405.407&quot;]">дальше</a>
            WebElement Paginate = driver2.findElement(By.linkText("дальше"));
            AddToLog(FilePath, " Next page " + ii);
            Paginate.click();

        }
        AddToLog(FilePath, " Overall calls from yandex to "+MySite+":" + Statis);
        driver2.quit();
    }

    // добавляем информацию в файл с помощью FileWriter
    private static void AddToLog(String filePath, String text) {
        text = "\n" + text + "   " + java.time.LocalDateTime.now();
        System.out.println("В журнал: " + text);
        File file = new File(filePath);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            fr.write(text);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

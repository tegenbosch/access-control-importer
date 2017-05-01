package nl.tegenbosch.accesscontrol.importer.web;

import nl.tegenbosch.accesscontrol.importer.dto.ImportResult;
import nl.tegenbosch.accesscontrol.importer.service.ImportService;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author a3aanwisse
 */
@Controller
public class IndexController {

    private static final Log LOG = LogFactory.getLog(IndexController.class);

    private final ImportService importService;

    public IndexController(ImportService importService) {
        this.importService = importService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(name = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam(value = "file") MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("warning", "Het bestand ontbreekt!");
            return "index";
        } else {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            LOG.info("------------------------------------------------------------------------------------");
            LOG.info(String.format("Start van verwerking van CSV bestand '%s'", file.getOriginalFilename()));
            ImportResult importResult;
            try {
                importResult = importService.process(file.getBytes());

                model.addAttribute("result", importResult);
                model.addAttribute("recordCount", importResult.getResult().size());
                model.addAttribute("filename", file.getOriginalFilename());
                stopWatch.stop();
                model.addAttribute("tooktime", stopWatch);
                LOG.info(String.format("Klaar met verwerking van CSV bestand '%s' met '%d' items in '%s'",
                        file.getOriginalFilename(), importResult.getResult().size(), stopWatch));
                LOG.info("------------------------------------------------------------------------------------");

            } catch (Exception e) {
                model.addAttribute("warning", e.toString());
            }

            return "index";
        }
    }
}
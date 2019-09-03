import metro.Metro;
import metroJson.MetroJson;
import org.jsoup.select.Elements;
import wikiHTMLParser.WikiHTMLParser;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

public class Main {
	public static void main(String[] args) throws IOException {
		Optional<Elements> elements = WikiHTMLParser.parse(new URL("https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0"));
		Metro metroFromWiki = WikiHTMLParser.getMetro(elements);
		MetroJson.save(metroFromWiki, Path.of("test.json"));
		Metro metroLoaded = MetroJson.load(Path.of("test.json"));
		System.out.println(metroFromWiki.equals(metroLoaded) ? "Metro's are identical" : "Something went wrong");

		metroLoaded.getStations().forEach((line, stationsList) -> System.out.println(String.format("There is %d stations at line %s ", stationsList.size(), line)));
	}
}

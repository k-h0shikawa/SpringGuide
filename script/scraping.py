from selenium import webdriver
from selenium.webdriver.common.by import By

class ScrapingBySelenium():

    URL = "https://spring.pleiades.io/guides"

    def __init__(self):
        options = webdriver.ChromeOptions()
        options.add_argument("--headless")
        self.driver = webdriver.Chrome(options)
    
    def scrape_url_and_h2_elements(self):
        self.driver.get(self.URL)
        elements = []
        for element in self.driver.find_elements(By.CLASS_NAME, "is-half"):
            h2_text = element.find_element(By.TAG_NAME, "h2").text
            url = element.find_element(By.TAG_NAME, "a").get_attribute("href")
            elements.append({"h2_text": h2_text, "url": url})
        return elements
        

if __name__ == "__main__":
    scraping_by_selenium = ScrapingBySelenium()
    elements = scraping_by_selenium.scrape_url_and_h2_elements()
    for e in elements:
        print(e["h2_text"])
        print(e["url"])
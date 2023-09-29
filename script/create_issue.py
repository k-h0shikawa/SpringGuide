import subprocess
from selenium import webdriver
from selenium.webdriver.common.by import By
import scraping
import github


scraping_by_selenium = scraping.ScrapingBySelenium()
elements = scraping_by_selenium.scrape_url_and_h2_elements()

github_cli = github.GithubCli()
PROJECT_NAME = "SpringGuide"

for element in elements:
    github_cli.create_issue(
        title=element["h2_text"], 
        body=element["url"], 
        project=PROJECT_NAME)

print("Issue has been created.")
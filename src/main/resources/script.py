from selenium import webdriver
from java.time.format import DateTimeFormatter
import momondo

driver = webdriver.Chrome()
pricewalker = momondo.PriceWalker(driver)

date_formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

def process_result(date_dep, date_dest, price):
    print "%s - %s : %s" % (date_dep, date_dest, price)

pricewalker.find_best_prices(
    dep_date.format(date_formatter),
    back_date.format(date_formatter),
    destination_preference.originAirportCode(),
    destination_preference.destinationAirportCode(),
    destination_preference.includeNearbyAirports(),
    process_result
)

driver.quit()
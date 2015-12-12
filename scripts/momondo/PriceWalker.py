from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from datetime import date

class PriceWalker:

    def __init__(self, driver=None):
        self.driver = driver
        self._search_timeout = 100

    def find_best_prices(self, dates, dep_airport, dest_airport, callback):
        for (dep_date, back_date) in dates:
            price = self._get_best_price(dep_airport, dest_airport, dep_date, back_date)
            callback(dep_date, back_date, price)

    def _get_best_price(self, dep, dest, dateThere, dateBack):
        fullUrl = "http://www.momondo.com/flightsearch/"
        fullUrl += "?Search=true&TripType=return&SegNo=2"
        fullUrl += "&SO0=%s&SD0=%s&SDP0=%s" % (dep, dest, dateThere.strftime("%d-%m-%Y"))
        fullUrl += "&SO1=%s&SD1=%s&SDP1=%s" % (dest, dep, dateBack.strftime("%d-%m-%Y"))
        fullUrl += "&AD=1&TK=ECO&DO=false&NA=false"
        self.driver.get(fullUrl)

        try:
            condition = EC.text_to_be_present_in_element((By.ID, "searchProgressText"), "Search complete")
            element = WebDriverWait(self.driver, self._search_timeout).until(condition)
        except:
            print "Unexpected error ", sys.exc_info()[0]
            return "---"

        best_deal_price_element = self.driver.find_element(By.CSS_SELECTOR, '#flight-tickets-sortbar-bestdeal .price')


        return best_deal_price_element.text

from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By

class PriceWalker:

    def __init__(self, driver=None):
        self.driver = driver
        self._search_timeout = 100

    def find_best_prices(self, dep_date, back_date, dep, dest, include_nearby, callback):
        fullUrl = "http://www.momondo.com/flightsearch/"
        fullUrl += "?Search=true&TripType=return&SegNo=2"
        fullUrl += "&SO0=%s&SD0=%s&SDP0=%s" % (dep, dest, dep_date)
        fullUrl += "&SO1=%s&SD1=%s&SDP1=%s" % (dest, dep, back_date)
        fullUrl += "&AD=1&TK=ECO&DO=false&NA=%s" % (str(include_nearby).lower())
        self.driver.get(fullUrl)

        try:
            condition = EC.text_to_be_present_in_element((By.ID, "searchProgressText"), "Search complete")
            element = WebDriverWait(self.driver, self._search_timeout).until(condition)
        except:
            print "Unexpected error ", sys.exc_info()[0]
            return "---"

        best_deal_price_element = self.driver.find_element(By.CSS_SELECTOR, '#flight-tickets-sortbar-bestdeal .price')
        print fullUrl
        callback(dep_date, back_date, best_deal_price_element.text)
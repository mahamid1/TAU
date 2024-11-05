# EasyCompare Chrome Extension

## Group Members
- Muhammad Taha
- Yuval Tzruya
- Rami Khatib
- Mohammad Mahamid
- Valeria Vaisman
- Alaa Haddad

## Project Overview
The EasyCompare Chrome extension enhances online shopping by finding the best deals for the product a user is viewing at the moment the extension is activated. Accessible directly from the browser, it scans multiple websites to identify the lowest prices while ensuring that the sellers are reliable, helping the user make informed purchasing decisions. Powered by AI, it also suggests alternative products to further enhance the shopping experience.
Demonstrations are available in the appendices.

## Running Instructions
First download the extension folder and all its contents.
To load the extension in Chrome in developer mode, follow these steps:
1. Open Chrome and navigate to chrome://extensions/.
2. Enable developer mode using the toggle switch in the top right corner.
3. Click on "Load unpacked" and select the "extension" folder that contains the extension files.
- The file "server.py" should be running while using the extension.

## Project Structure
The project consists of the following files:

- manifest.json: Defines the extension's metadata and permissions.
- popup.html: The HTML file for the extension's popup interface.
- popup.css: The CSS file for styling the popup interface.
- popup.js: Handles the logic for the popup interface, including user input and initiating searches.
- index.js: File responsible for creating and managing the user interface and interactions.
- search.js: Contains functions for handling product searches, fetching store ratings (using the Python server), fetching product information and suggesting similar products.
- App.css: The CSS file for styling the results webpage interface.
- searchResults.html: Displays the search results to the user.
- server.py: Backend server script using Flask to search store ratings and process them.
- Files for design assets: an icon, logo images, and a custom font.

## Algorithms and Features

#### Extension Popup
When the user navigates to a product page and activates the extension, a call is made to the **getProductName function** located in the popup.js file. This function uses the OpenAI API to extract the product name from the page's title and inserts it into the search bar within the popup. If the user is on a page that doesn't contain a product, the search bar remains empty until the user manually enters a search term.
Once the user clicks the search button (or presses Enter), the search term is passed as input to the **searchProduct function** in the search.js file, and the search button is disabled until a new tab with search results is opened.

#### Searching Algorithm - searchProduct Function
This function utilizes the Google API by calling the **googleProductSearch function**, which takes the search term and site link as inputs to search for the product across 10 pre-defined websites (as listed in the appendices) using a custom search engine. For each site, we search for up to 5 search results (usually there are less than 5 relevant products in each site). 
All search results from the sites are combined into a single array, and the **extractProductInfo** function (search.js file) is called in a loop for each search result. This function extracts the relevant information for each search result. It first checks whether the product is relevant to the user's search by calling the **isProductRelevant function** (search.js file), which receives the search term and the found product description as input and, using the OpenAI API, returns whether the product is relevant. If the product is not relevant, the desired information (price, image, and a more accurate description if available) is not extracted. Additionally, for each website that displays products that may be out of stock, a check is performed to determine the product's availability using one of the following functions, depending on where this information is available on the site: **checkAvailability**, **fetchPriceFromHtml** (both gets product link and store name as inputs), or one of the functions that retrieve data from the Google API response. Products that are found to be out of stock are not saved in the results. 

For each store where the product is found, the **function fetchStoreRating** (search.js file) is called. It receives the store's name as input and calls the **get_ratings function** in the server.py file. The get_ratings function searches Google for "{store name} ביקורות" by calling the **get_google_search_ratings function** (server.py file), which extracts all the ratings from the HTML of the search results and returns their average.
An array of products is created, where each product has the following fields: product name, link, price, image, store name, store logo, and store rating.

Next, the function then calls **getSimilarProducts** (search.js file), which receives the search term as input and, using the OpenAI API, returns an array of up to 6 names of products similar to the one the user searched for. The code then iterates over each product name in this array and performs search using the **googleProductSearch function** again. This time for each similar product we take only the first search result. The function is called only once for each similar product, and the search is not restricted to a specific site but rather conducted across all predefined sites. The similiar products search results combined into a single array, then the code iterates over the array and calls **extractInfoForSimilarItems function** for each similar product. It gets the similar product name and the title of the search result as inputs, checks if it the search result is relevant (using isProductRelevant function again), and if it is relevent, extracts the an image of the similar product. An array of similar products is created, where each product has the fields product name and image.

These results of similar products are then stored in the browser's local storage, along with the main search results.
Finally, the code opens a new tab to display the search results, including the similar products, by navigating to a local HTML file (searchResults.html).

#### Results Display and Management
When the search results page is loaded, the **createApp function** located in index.js file is triggered to set up the UI.  It sets up the header, the sort and filter controls, and the table that will hold the search results. It also initiates the first rendering of the search results, which are displayed sorted by a weighted score prioritizing low prices and high store ratings. The search results are taken from browser's local storage.

The function that is called to sort the results is **sortProducts**. It gets as an input the sorting criteria: combined score (descending order), price (ascending or descending order) and store rating (descending order). If the criteria is combined score, it is calculated by the **calculateCombinedScore function** (in index.js) that generates a score for each product by normalizing both its price and rating, then subtracting the normalized price from the normalized rating.

The **createStarRating function** (in index.js) generates a visual star rating element based on the given rating value, where it displays up to 5 stars, and returns this star rating as a styled HTML element.

The **renderProducts function** (in index.js) is responsible for rendering the sorted and filtered search results into a table format. For each product, it displays the image, price, product name, store logo, store rating, and a "Go to Store" button. This function ensures that the UI is dynamically updated based on the current sorting criteria.

The **filterByPrice function** filters the search results based on a specified minimum and maximum price range, storing the filtered results in the browser's local storage and reapplying the current sorting criteria. If the user decides to remove the price filter (by clicking the "remove filter" button), the code reverts to the original, unfiltered search results stored in local storage. The sorting function is then called again to ensure that the products are still sorted according to the selected criteria.

The **createSimilarProductsSection function** (in index.js) adds a section to the page that displays similar products to the one originally searched. This section retrieves the similar product search results (if available, otherwise the category does not appear) from the browser's local storage and displays them at the bottom of the page. Each product is shown with an image, name, and a button that users can click to search for that specific product.



## Prompt/Algorithm Development
#### Automatic Product Detection
Our project began with a Chrome extension that required manual input for searching products on e-commerce websites. As we progressed, we enhanced the functionality so that when the user activates the extension from a specific product page, the product name is automatically extracted using the OpenAI API and entered into the search bar of the popup. The user still retains the option to manually enter input if they choose to do so.

#### Expansion of Supported Websites
Since extracting the product image and price from different fields on each website required custom code adaptations for each site, we initially focused on retrieving information from just two websites. Gradually, we expanded the list of websites where we perform searches and extract relevant data, ultimately covering 10 different sites.

#### Filtering out irrelevant results
We initially tried to filter unrelated products by categorizing them based on available HTML data. However, due to inconsistent category information across different websites, we shifted to using the OpenAI API for product filtering. We crafted a prompt that asked whether the product in the search results matched the user's query, initially providing the product link and utilizing the GPT-4o model. As we refined our approach, we transitioned to the GPT-4o mini model and began sending the product description instead of the link. This change significantly enhanced processing speed.

#### Sorting Results
Initially, our system only sorted the results by price, but as we continued to improve the project, we introduced a "rating" for each store and incorporated this rating into the sorting algorithm to provide users with more comprehensive search results.

#### AI-Based Product Recommendations
After successfully using AI to filter products and identify the correct product on a webpage, we were inspired to leverage it further by implementing a feature that suggests similar products. This AI-powered recommendation system provides users with additional options based on their search, enhancing their shopping experience by offering alternative products that may also meet their needs.

## Appendices
### Demo
https://www.youtube.com/watch?v=1AwfPWCHmjc - Iphone 15 pro 256gb search

https://www.youtube.com/watch?v=MgNf-0HOKYs - pink Iphone 15 plus 512gb search

https://www.youtube.com/watch?v=Me7FXQbj0zc - Kitchenaid 5KSM175 mixer search


### Supported Websites
- KSP
- Payngo (מחסני חשמל)
- Anakshop (ענק המחשבים)
- Bug
- Lastprice
- Ivory
- Amirim (אמירים הפצה)
- Netoneto (חשמל נטו)
- Shufersal Online
- Mashbir (365 משביר)

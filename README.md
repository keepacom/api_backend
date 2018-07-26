<!--
  Copyright 2018 Keepa.com GmbH
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
    http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->

Keepa API Framework
==============================

This framework is intended for users of the Keepa API.

<a name="features"></a>Features
--------
* Retrieves content from the API asynchronously and in parallel via Deferred callbacks (Java 8 Lambda friendly)
* Parses API response to easy to use Java objects
* Provides methods that facilitate the work with price history data

Maven
-----
```xml
<repositories>
	<repository>
		<id>Keepa</id>
		<name>Keepa Repository</name>
        <url>https://keepa.com/maven/</url>
    </repository>
	...
</repositories>

<dependencies>
	<dependency>
		<groupId>com.keepa.api</groupId>
		<artifactId>backend</artifactId>
		<version>LATEST</version>
	</dependency>
	...
</dependencies>
```

Gradle
-----
```xml
repositories {
  ...
  maven { url 'https://keepa.com/maven/' }
}
dependencies {
  ...
  compile 'com.keepa.api:backend:latest.release'
}

Also consider to add

configurations.all {
    resolutionStrategy {
        cacheDynamicVersionsFor 0, 'seconds'
        cacheChangingModulesFor 0, 'seconds'
    }
}

Which makes sure that the newest version from our servers is pulled during build.
```


<a name="examples"></a>Quick Example
==============

<a name="examples-keepa-api"></a>Make an API request
---------------------------

```java
KeepaAPI api = new KeepaAPI("YOUR_API_KEY");
Request r = Request.getProductRequest(AmazonLocale.US, 90, null, "B001GZ6QEC");

api.sendRequest(r)
		.done(result -> {
			switch (result.status) {
				case OK:
					// iterate over received product information
					for (Product product : result.products){
						// System.out.println(product);
						if (product.productType == Product.ProductType.STANDARD.code || product.productType == Product.ProductType.DOWNLOADABLE.code) {

							//get basic data of product and print to stdout
							int currentAmazonPrice = ProductAnalyzer.getLast(product.csv[Product.CsvType.AMAZON.index], Product.CsvType.AMAZON);

							//check if the product is in stock -1 -> out of stock
							if (currentAmazonPrice == -1) {
								System.out.println(product.asin + " " + product.title + " is currently out of stock!");
							} else {
								System.out.println(product.asin + " " + product.title + " Current Amazon Price: " + currentAmazonPrice);
							}
							
							// get weighted mean of the last 90 days for Amazon
							int weightedMean90days = ProductAnalyzer.calcWeightedMean(product.csv[Product.CsvType.AMAZON.index], KeepaTime.nowMinutes(), 90, Product.CsvType.AMAZON);

							...
						} else {
							...
						}
					}
					break;
				default:
					System.out.println(result);
			}
		})
		.fail(failure -> System.out.println(failure));

```

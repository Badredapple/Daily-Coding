#	This is from PythonCookBook
#	Practice in Vim
#	This the frist file in this poject
# 	Use service throught HTTP poxy 
#	2016/3/18
from urllib imort request,parse
# Base URL being accssed
 url = 'http://httpin.org/get

# Dictonary of query parameters (if any)
 parms = {
 	'name1' : 'value1',
 	'name2' : 'value2'
 }

 # Encode the query string
 
 querystring = parse.urlencode(parms)
 #Make a get requset and read the reponse
 u = requset.urlopen(url + '?' + querystring)
 resp = u.read()

- -----------------------------------------------------------
-------------------------------------------------------------

# if use POST spend a packet in request body .We will use like this

from urllib import ,parse

# Base URL being acessed
url = 'http://httpin.org/post'

#Dictionary of query paraments (if any)

parms = { 
	'name1': 'value1'
	'name2': 'value2'
	}

#Encode the query string
querystring = parse.urlencode(parms)

#Make a Post request and read the response
u = request.urlopen(url, querystring.encode('ascii'))
resp =u.read()


--------------------------------------------------------------------------
--------------------------------------------------------------------------

# Maybe we could use some init headers

from urllib import request,parse

......

#Extra headers
headers = {
	'User-agent' : 'none/ofyoubusiness',
	'Spam' : 'Eggs'
	}

req = request.Request(url ,querystring.encode('ascii'), hearders=headers)

#make a request and read the response
u = request.urlopen(req)
resp = u.read()

--------------------------------------------------------------------------
--------------------------------------------------------------------------
# If you have read request lib ,you will konw ,we could use function like this:

import requests

#Base URL being accessed
url = 'http://httpin.org/post'


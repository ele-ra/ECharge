This is an app to manage charging events of electric vehicles. 
User from the list of known users can login into the app and receive a token to perform operations in the system.


Open endpoint provided to show App version and Db version defined in app properties. It is accessible with path:

    /info/version

Only for Admin user:

    GET request
        /api/admin/chargingsessions

List charging sessions with option to filter by dates using path variables <from> and <to>,
where the date in supported formats should be entered. Supported formats are:
"yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy-MM-dd", "yyyy-MM-dd HH:mm"
    
    POST request
        /api/admin/connector/create
Add new connector to the system linking it to an existing charge point. 
It is not possible to add connector without a link to charge point.

    Parameters needed:
        {
        "chargePointName": "1",
        "chargePointSerialNumber": "sn1",
        "connectorNumber": 1
        }
    Parameters types:
        chargePointName <string>
        chargePointSerialNumber <string>
        connectorNumber accepts <int number not more than 5 digits long>
Returns info about added connector or a message with error information.

For Customer user:

    POST request
        /api/charge/start
Start charging session providing charge point Serial Number, connector number and RDFIDTagNumber. 
It is only possible to initiate charging session, if provided parameters are valid and can be used. 
System will provide more information with error messages. 
    
    Parameters needed:
        {
        "chargePointSerialNumber": "sn1",
        "connectorNumber": 1,
        "rdfidTagNumber": 1
        }
    Parameters types:
        chargePointSerialNumber <string>
        connectorNumber <int number not more than 5 digits long>
        rdfidTagNumber <int number not more than 5 digits long>
Returns info about created session or a message with error information.

    POST request
        /api/charge/end
End charging session providing charge point Serial Number, connector number and RDFIDTagNumber.
It is only possible to end charging session if it exists and if provided parameters are valid and can be used.

    Parameters needed:
        {
        "chargePointSerialNumber": "sn1",
        "connectorNumber": 1,
        "rdfidTagNumber": 1
        }
    Parameters types:
        chargePointSerialNumber <string>
        connectorNumber <int number not more than 5 digits long>
        rdfidTagNumber <int number not more than 5 digits long>    
Returns info about ended session or a message with error information.

For User: 

    POST request
        /api/auth/login
Should be used to login into application using credentials of known users.

    Existing users:
        User1
        User2
        User3
        Admin
    Password for all of them: password.
    Parameters needed:
        {
        "username": "Admin",
        "password": "password"
        }
    Parameters types:
        username <string>
        password <string>
    
Upon successful authorization, generated jwt token will be returned. 
It is needed to be hold in headers of request to continue using application. 

    Example:
        bearer: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImlhdCI6MTY2ODM3NTM5OCwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1fQ.JT8aQdLNUv1ffnJZY2a2FEOjgkFrFh9ydbzBb7hsfEMUAtWsFcTwPPdCYgT7QhoVZiWAtz0AQjfOsc_pXE5nhA
    

Here are some curl examples for API usage.

Login as user:

    curl --location --request POST "http://localhost:8080/api/auth/login" ^
    --header "Content-Type: application/json" ^
    --header "Cookie: JSESSIONID=4E3764B9DFCBA95C2F80D6624D5A0DF7" ^
    --data-raw "{\"username\": \"User1\", \"password\": \"password\"}"

Start a session:

    curl --location --request POST "http://localhost:8080/api/customer/charge/start" ^
    --header "token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImlhdCI6MTY2ODM2MzEwOCwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1fQ.ymTGVaaNcqMI_lJyxc0ZAAjOw3AjKHei_O_GMQeQln4FvaXWC0ZQJ4Qdm1pz9lPBpi5tMVvLtI8al_vjKYkjWA" ^
    --header "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMSIsImlhdCI6MTY2ODM2OTQ3NiwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1fQ.HVrEvL_N4L4cK4nAJJfCASf2WggKPG0hmH30OSE-i4xhmMu5JNLQsJt6F1TFnTFxOUBsjuIE-udITWUumr41Dw" ^
    --header "Content-Type: application/json" ^
    --header "Cookie: JSESSIONID=4E3764B9DFCBA95C2F80D6624D5A0DF7" ^
    --data-raw "{\"chargePointSerialNumber\": \"sn1\",\"connectorNumber\": 1,\"rdfidTagNumber\": 1}"

End a session:

    curl --location --request POST "http://localhost:8080/api/customer/charge/end" ^
    --header "token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImlhdCI6MTY2ODM2MzEwOCwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1fQ.ymTGVaaNcqMI_lJyxc0ZAAjOw3AjKHei_O_GMQeQln4FvaXWC0ZQJ4Qdm1pz9lPBpi5tMVvLtI8al_vjKYkjWA" ^
    --header "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMSIsImlhdCI6MTY2ODM2OTQ3NiwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1fQ.HVrEvL_N4L4cK4nAJJfCASf2WggKPG0hmH30OSE-i4xhmMu5JNLQsJt6F1TFnTFxOUBsjuIE-udITWUumr41Dw" ^
    --header "Content-Type: application/json" ^
    --header "Cookie: JSESSIONID=4E3764B9DFCBA95C2F80D6624D5A0DF7" ^
    --data-raw "{\"chargePointSerialNumber\": \"sn1\",\"connectorNumber\": 1,\"rdfidTagNumber\": 1}"

Login as admin user:

    curl --location --request POST "http://localhost:8080/api/auth/login" ^
    --header "Content-Type: application/json" ^
    --header "Cookie: JSESSIONID=4E3764B9DFCBA95C2F80D6624D5A0DF7" ^
    --data-raw "{\"username\": \"Admin\", \"password\": \"password\"}"

View sessions:

    curl --location --request GET "http://localhost:8080/api/admin/chargingsessions" ^
    --header "from: 2000-10-12 14:30" ^
    --header "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMSIsImlhdCI6MTY2ODM2OTQ3NiwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1fQ.HVrEvL_N4L4cK4nAJJfCASf2WggKPG0hmH30OSE-i4xhmMu5JNLQsJt6F1TFnTFxOUBsjuIE-udITWUumr41Dw" ^
    --header "Cookie: JSESSIONID=4E3764B9DFCBA95C2F80D6624D5A0DF7"

Add connector:

    curl --location --request POST "http://localhost:8080/api/admin/connector/create" ^
    --header "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImlhdCI6MTY2ODM3NTM5OCwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1fQ.JT8aQdLNUv1ffnJZY2a2FEOjgkFrFh9ydbzBb7hsfEMUAtWsFcTwPPdCYgT7QhoVZiWAtz0AQjfOsc_pXE5nhA" ^
    --header "Content-Type: application/json" ^
    --header "Cookie: JSESSIONID=4E3764B9DFCBA95C2F80D6624D5A0DF7" ^
    --data-raw "{\"chargePointName\": \"1\", \"chargePointSerialNumber\": \"sn1\", \"connectorNumber\": 111}"



    







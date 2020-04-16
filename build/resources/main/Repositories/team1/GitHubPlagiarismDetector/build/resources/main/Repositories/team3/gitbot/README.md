# gitbot
A bot to access github using git rest-api.

#### LIBRARIES


`$ import requests`
-  to use get & post methods to interact with git rest-api

`$ import json`
-  library to encode/decode json data

###### There are total 6 function calls, each related to specific tasks mentioned below.
 
#### TASK 1.1 - List Branches

- Line: 69 `$ listBranch('your_username','repo_name')`
- Function takes first parameter as any (username) which is registered on Github & the name of particular repository whose owner is (username).
- Function will return the list of all branches created inside this repository.

Code Blocks (Preformatted text):

    resp = requests.get('https://api.github.com/repos/' + username + "/" + repo)
	gh = resp.json()
- here, gh gets the data from the above mentioned link in the jsin format which we can access to list branches.

#### TASK 1.2 - Create Repository

- Line: 71 `$ createRepo('your_username','your_password','repo_name')`
- Function takes first parameter as your (username) & second as your (password) for authorization on github.
- Third parameter is the name of repository you want to create.

In the function: createRepo()

    data = {"name": name}
    data_json = json.dumps(data)
	r = requests.post('https://api.github.com/user/repos', data_json, auth=(username, password))

- here, data is our new json which contains data about new repository. This sample data contains only "name" field of repository. 
- Then, request is post to the above mentioned URL with our json data.

#### TASK 1.3 - Create Issue

- Line: 73 `$ createIssue('your_username','your_password','repo_name',"issue_name")`
- Function takes first parameter as your (username) & second as your (password) for authorization on github. 
- Third parameter is an existing repository name of that user in which you want to create the issue.
- Fourth is the title of issue which you want to create.

Code Blocks (Preformatted text):

    r = requests.post('https://api.github.com/repos/' + username + "/" + repo + "/issues", data_json, auth=(username, password))
	
- here, request json data is post to mentioned URL with the title of issue mentioned inside "data_json".

#### TASK 1.4 - Add Assignee

- Line: 75 `$ addAssignee('your_username', 'your_password', 'repo_name', 1, "assignee_username")`
- Function takes first parameter as your (username) & second as your (password) for authorization on github. 
- Third parameter is an existing repository name of the user.
- Fourth is the id of the issue in which you want to add the assignee.
- Fifth is the username of an assignee you want to add.

Code Blocks (Preformatted text):

    r = requests.post(
        'https://api.github.com/repos/' + username + '/' + repo + '/issues/' + str(issueid) + '/assignees', data_json, auth=(username, password))
- here, request is post to above mentioned URL where assignee is added to the issue id given.

#### TASK 1.5 - Edit Rpository

- Line: 77 `$editRepo('your_username','your_password$','repo_name','new_name','hasIssue'))`
- Function takes first parameter as your (username) & second as your (password) for authorization on github. 
- Third parameter is an existing repository name of the user.
- Fourth is the new name of the repository you want to assign.
- Fifth is Boolean value whether you want to set issues to false or true.

Code Blocks (Preformatted text):

    r = requests.patch('https://api.github.com/repos/' + username + '/' + repo, data_json, auth=(username, password))
- here, request goes to the URL mentioned above with authorization. It renames the repository and also changes the status of has_issues according to the data passed in function by user.

#### TASK 1.6 - List Reactions

- Line: 79 `$ listreactons('your_username', 'repo_name', 'issue_id'))`
- Function takes first parameter as any (username) which is registered on Github & the name of particular repository whose owner is (username).
- Third parameter is "issue_id" of the issue for retrieving reactions in it.
- Function will print all the reactions inside an issue.

Code Blocks (Preformatted text):

    headers = {'Accept': 'application/vnd.github.squirrel-girl-preview+json'}
    r = requests.get('https://api.github.com/repos/' + username + "/" + repo + "/issues/" + str(id) + '/reactions', headers=headers)
- here, Custom accept header is used as mentioned in "github api developer guide" to fetch data from above mentioned url

#### To run & build your code:

- Please replace 'your_username' & 'your_password' with the specific authorized username on github & corresponding password respectively.
- Replace 'repo_name' in Task 1.1, 1.3, 1.4 ,1.5 & 1.6 to the existing repository you want to access
- Replace 'repo_name' in Task 1.2 to the name of repository you want to create.
- Replace 'issue_name' in Task 1.3 to the name of issue you want to generate
- Replace 'assignee_username' in Task 1.4 to name of the assignee you want to add to issue.
- Replace 'new_name' in task 1.5 to new name you want to assign to repo while editing & set hasIssue as "true or false".
- Replace 'issue_id' in task 1.6 to issue id number you want to access.

#### References:
- Github Developer Guide-> https://developer.github.com/v3/


### End


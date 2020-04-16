import requests
import json


def listBranch(username, repo):
    resp = requests.get('https://api.github.com/repos/' + username + "/" + repo)
    gh = resp.json()
    branch_url = gh['branches_url']
    size = len(branch_url) - 9
    branch_url = branch_url[0:(size)]
    counter = 0
    branches = requests.get(branch_url)
    for branch in branches.json():
        print("branch: " + branch['name'])
        counter += 1


def createRepo(username, password, name):
    print('---Task 1.2---')
    data = {"name": name}
    data_json = json.dumps(data)
    r = requests.post('https://api.github.com/user/repos', data_json, auth=(username, password))
    print(r.json())


def createIssue(username, password, repo, title):
    print('---Task 1.3---')
    data = {"title": title}
    data_json = json.dumps(data)
    r = requests.post('https://api.github.com/repos/' + username + "/" + repo + "/issues", data_json,
                      auth=(username, password))
    print(r.json())


def addAssignee(username, password, repo, issueid, assignname):
    print('---Task 1.4---')
    header = {'Accept': 'application/vnd.github.symmetra-preview+json'}
    data = {"assignees": assignname}
    data_json = json.dumps(data)
    r = requests.post(
        'https://api.github.com/repos/' + username + '/' + repo + '/issues/' + str(issueid) + '/assignees', data_json,
        auth=(username, password))
    print(r.json())


def editRepo(username, password, repo, new_name , has_issue):
    print('---Task 1.5---')
    header = {'Accept': 'application/vnd.github.symmetra-preview+json'}
    data = {"name": new_name, "has_issues": has_issue}
    data_json = json.dumps(data)
    r = requests.patch('https://api.github.com/repos/' + username + '/' + repo, data_json, auth=(username, password))
    print(r.json())


#
def listreactons(username, repo, id):
    print('---Task 1.6---')
    headers = {'Accept': 'application/vnd.github.squirrel-girl-preview+json'}
    r = requests.get('https://api.github.com/repos/' + username + "/" + repo + "/issues/" + str(id) + '/reactions',
                     headers=headers)
    for reaction in r.json():
        print(reaction['content'])


#Task 1.1
listBranch('your_username','repo_name')
#Task 1.2
createRepo('your_username','your_password','repo_name')
#Task 1.3
createIssue('your_username','your_password','repo_name',"issue_name")
#Task 1.4
addAssignee('your_username', 'your_password', 'repo_name', 1, "assignee_username")
#Task 1.5
editRepo('your_username','your_password','repo_name','new_name','hasIssue')
#Task 1.6
listreactons('your_username', 'repo_name', 'issue_id')

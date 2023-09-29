import subprocess
import time

class GithubCli():
 
    command = ["gh", "issue", "create"]

    sleep_time = 5
    
    def __init__(self):
        self.command.extend(["--assignee", "k-h0shikawa"])
    
    def create_issue(self, title, body, project):
        self.command.extend(["--title", title])
        self.command.extend(["--body", body])
        self.command.extend(["--project", project])
        subprocess.run(self.command)

        # APIを短時間で連続実行しないようにスリープを入れる
        time.sleep(self.sleep_time)

# デバッグ用
if __name__ == "__main__":
    cli = GithubCli()
    cli.create_issue(
        title="test_title", 
        body="test_body", 
        project="SpringGuide")
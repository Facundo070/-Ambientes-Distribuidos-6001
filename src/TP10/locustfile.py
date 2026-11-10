from locust import HttpUser, task, between

class JWTUser(HttpUser):

    wait_time = between(1, 2)

    token = None

    def on_start(self):

        response = self.client.post(
            "/login",
            json={
                "username": "admin",
                "password": "1234"
            },
            verify=False
        )

        if response.status_code == 200:
            self.token = response.json()["token"]

    @task(3)
    def perfil(self):

        if self.token:
            self.client.get(
                "/perfil",
                headers={
                    "Authorization": f"Bearer {self.token}"
                },
                verify=False
            )

    @task(1)
    def login(self):

        self.client.post(
            "/login",
            json={
                "username": "admin",
                "password": "1234"
            },
            verify=False
        )
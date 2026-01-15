const BASE_URL = "http://localhost:8080";

export async function post(url, data) {
  const res = await fetch(BASE_URL + url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });
  return res.json();
}

export async function get(url) {
  const res = await fetch(BASE_URL + url);
  return res.json();
}

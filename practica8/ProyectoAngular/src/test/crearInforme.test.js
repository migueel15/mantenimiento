import { browser } from "k6/browser";
import { check } from "https://jslib.k6.io/k6-utils/1.5.0/index.js";

export const options = {
  scenarios: {
    ui: {
      executor: "shared-iterations",
      options: { browser: { type: "chromium" } },
      vus: 1,
      iterations: 1,
    },
  },
};

export default async function() {
  const page = await browser.newPage();

  try {
    await page.goto("http://localhost:4200");

    await page.locator("input[name='nombre']").type("Manuel");
    await page.locator("input[name='DNI']").type("123");
    const login = page.locator("button[name='login']");

    await Promise.all([page.waitForNavigation(), login.click()]);

    check(page.locator("h2"), {
      "Se carga la pagina home": async (title) => {
        return (await title.textContent()) === "Listado de pacientes";
      },
    });
    // esperamos a que aparezcan los pacientes
    await page.waitForSelector("td[title='Miguel']");
    // clicamos sobre el paciente pepe
    await page.locator("td[title='Miguel']").click();

    await page.waitForSelector("button[name='view']");
    await page.locator("button[name='view']").click();

    await page.waitForSelector("button[name='add']");
    await page.locator("button[name='add']").click();

    await page.locator("button.predict-button").click();
    await page
      .locator("textarea[matinput]")
      .type("El paciente parece no tener ningún sintoma");
    await page.locator('button[name="save"]').click();

    check(null, {
      "Informe guardado exitosamente": async () => {
        return (await page.content()).includes("Informe de la imagen");
      },
    });
  } finally {
    await page.close();
  }
}

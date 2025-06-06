import { check } from "k6";
import { browser } from "k6/browser";

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
  await page.goto("http://localhost:4200");

  await page.locator("input[name='nombre']").type("Manuel");
  await page.locator("input[name='DNI']").type("123");
  await page.locator("button[name='login']").click();

  await page.waitForNavigation();
  await check(page.locator("h2"), {
    header: async (lo) => {
      (await lo.textContent()) === "Listado de pacientes";
    },
  });
}

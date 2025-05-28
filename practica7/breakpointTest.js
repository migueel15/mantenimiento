import http from "k6/http";
import { check } from "k6";

export const options = {
	thresholds: {
		http_req_failed: [
			{
				threshold: "rate<=0.01",
				abortOnFail: true,
			},
		],
	},
	stages: [{ target: 100000, duration: "10m" }],
};

export default function () {
	const res = http.get("http://localhost:8080/medico/1");
	check(res, {
		"response code was 200": (r) => r.status === 200,
	});
}

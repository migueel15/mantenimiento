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
	scenarios: {
		break_server: {
			executor: "ramping-arrival-rate",
			startRate: 100,
			timeUnit: "1s",
			preAllocatedVUs: 1000,
			maxVUs: 1000000,
			stages: [{ target: 100000, duration: "10m" }],
		},
	},
};

export default function () {
	const res = http.get("http://localhost:8080/medico/1");
	check(res, {
		"response code was 200": (r) => r.status === 200,
	});
}

"use client";

import { useState } from "react";
import { useForm } from "react-hook-form";

const feedbackSchema = {
  agreement: {
    label: "Was this recommendation useful?",
    type: "radio",
    options: [
      { label: "👍 Agree", value: "agree" },
      { label: "🤔 Partially Agree", value: "partially_agree" },
      { label: "👎 Disagree", value: "disagree" },
    ],
  },
  confidence: {
    label: "How confident are you?",
    type: "select",
    options: [
      { label: "High", value: "high" },
      { label: "Medium", value: "medium" },
      { label: "Low", value: "low" },
    ],
  },
  comment: {
    label: "Optional comment",
    placeholder: "What was wrong?",
    type: "textarea",
  },
};

export default function DecisionFeedbackForm({ scenarioId }) {
  const [status, setStatus] = useState("idle");

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      agreement: "",
      confidence: "medium",
      comment: "",
    },
  });

  const onSubmit = async (values) => {
    if (!scenarioId) {
      setStatus("missing-scenario");
      return;
    }

    setStatus("submitting");

    try {
      const response = await fetch("http://localhost:8080/api/feedback", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          scenarioId,
          agreement: values.agreement,
          confidence: values.confidence,
          comment: values.comment,
        }),
      });

      if (!response.ok) {
        throw new Error("Feedback request failed");
      }

      setStatus("success");
      reset({
        agreement: "",
        confidence: "medium",
        comment: "",
      });
    } catch (error) {
      console.error(error);
      setStatus("error");
    }
  };

  return (
    <section className="mt-8 rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
      <h2 className="text-lg font-semibold text-slate-900">
        Was this recommendation useful?
      </h2>

      <p className="mt-1 text-sm text-slate-500">
        Your feedback helps UrbanFlow collect better decision data for future improvements.
      </p>

      <form onSubmit={handleSubmit(onSubmit)} className="mt-5 space-y-5">
        <div>
          <div className="grid gap-3 sm:grid-cols-3">
            {feedbackSchema.agreement.options.map((option) => (
              <label
                key={option.value}
                className="cursor-pointer rounded-xl border border-slate-200 p-4 text-sm font-medium text-slate-700 transition hover:border-blue-400 hover:bg-blue-50"
              >
                <input
                  type="radio"
                  value={option.value}
                  className="mr-2"
                  {...register("agreement", {
                    required: "Please select one option.",
                  })}
                />
                {option.label}
              </label>
            ))}
          </div>

          {errors.agreement && (
            <p className="mt-2 text-sm text-red-600">
              {errors.agreement.message}
            </p>
          )}
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-700">
            {feedbackSchema.confidence.label}
          </label>

          <select
            className="w-full rounded-xl border border-slate-200 px-3 py-2 text-sm outline-none focus:border-blue-500"
            {...register("confidence", {
              required: true,
            })}
          >
            {feedbackSchema.confidence.options.map((option) => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-700">
            {feedbackSchema.comment.label}
          </label>

          <textarea
            rows={4}
            placeholder={feedbackSchema.comment.placeholder}
            className="w-full resize-none rounded-xl border border-slate-200 px-3 py-2 text-sm outline-none focus:border-blue-500"
            {...register("comment")}
          />
        </div>

        <button
          type="submit"
          disabled={status === "submitting"}
          className="rounded-xl bg-blue-600 px-5 py-2.5 text-sm font-semibold text-white transition hover:bg-blue-700 disabled:cursor-not-allowed disabled:bg-slate-400"
        >
          {status === "submitting" ? "Submitting..." : "Submit Feedback"}
        </button>

        {status === "success" && (
          <p className="text-sm text-green-600">
            Thanks! Your feedback was collected successfully.
          </p>
        )}

        {status === "error" && (
          <p className="text-sm text-red-600">
            Something went wrong while submitting feedback.
          </p>
        )}

        {status === "missing-scenario" && (
          <p className="text-sm text-red-600">
            Scenario ID is missing. Please save the analysis first.
          </p>
        )}
      </form>
    </section>
  );
}

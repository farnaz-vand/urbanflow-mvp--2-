"use client";

import React, { useState } from "react";
import { useForm } from "react-hook-form";

interface FeedbackProps {
  scenarioId: number;
}

interface FeedbackInputs {
  agreement: string;
  confidence: string;
  comment: string;
}

export default function DecisionFeedbackForm({ scenarioId }: FeedbackProps) {
  const [status, setStatus] = useState<"idle" | "submitting" | "success" | "error" | "missing-scenario">("idle");

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FeedbackInputs>({
    defaultValues: {
      agreement: "agree",
      confidence: "medium",
      comment: "",
    },
  });

  const onSubmit = async (values: FeedbackInputs) => {
    console.log("FORM SUBMITTED");
    console.log("FORM VALUES:", values);
    
    if (!scenarioId) {
      setStatus("missing-scenario");
      return;
    }

    setStatus("submitting");

    try {
      const payload = {
        // ✅ اصلاح مهم: استفاده از scenarioId واقعی به جای عدد 1
        scenarioId: Number(scenarioId), 
        userDecision: values.agreement.toUpperCase().replace(" ", "_"),
        confidence: values.confidence.toUpperCase(),
        reason: values.comment || "No comment provided",
        selectedAlternative: "" 
      };

      console.log("Sending payload:", payload);

      const response = await fetch("http://127.0.0.1:8080/api/feedback", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`HTTP Error ${response.status}: ${errorText}`);
      }

      setStatus("success");
      reset();
    } catch (error) {
      console.error("Feedback submission failed:", error);
      setStatus("error");
    }
  };

  if (status === "success") {
    return (
      <div className="rounded-2xl border border-emerald-500/30 bg-emerald-500/10 p-6 text-center backdrop-blur-md shadow-lg">
        <div className="mx-auto mb-3 flex h-12 w-12 items-center justify-center rounded-full bg-emerald-500/20 text-2xl">
          ✅
        </div>
        <h3 className="text-lg font-bold text-emerald-400">Thank You!</h3>
        <p className="mt-2 text-sm text-emerald-200/70">
          Your expert feedback has been recorded successfully and will help improve our AI models.
        </p>
        <button 
          onClick={() => setStatus("idle")}
          className="mt-5 text-xs font-semibold uppercase tracking-wider text-emerald-400 transition hover:text-emerald-300 hover:underline"
        >
          Submit another feedback
        </button>
      </div>
    );
  }

  return (
    <div className="rounded-2xl border border-white/10 bg-white/5 p-6 backdrop-blur-md shadow-lg">
      <div className="flex items-center gap-2 mb-1">
        <span className="text-xl">💡</span>
        <h3 className="text-xl font-bold text-white">Decision Feedback</h3>
      </div>
      <p className="text-sm text-slate-400">Help us improve the AI by sharing your expert judgment.</p>

      <form onSubmit={handleSubmit(onSubmit)} className="mt-6 space-y-5">
        <div>
          <label className="text-sm font-medium text-slate-300">Was this recommendation useful?</label>
          <div className="mt-3 flex flex-wrap gap-3">
            {["agree", "partially agree", "disagree"].map((option) => (
              <label key={option} className="relative flex cursor-pointer items-center">
                <input
                  type="radio"
                  {...register("agreement")}
                  value={option}
                  className="peer sr-only"
                />
                <span className="rounded-xl border border-white/10 bg-white/5 px-4 py-2.5 text-sm text-slate-400 transition-all peer-checked:border-brand-500 peer-checked:bg-brand-500/10 peer-checked:text-brand-400 hover:bg-white/10">
                  {option.charAt(0).toUpperCase() + option.slice(1)}
                </span>
              </label>
            ))}
          </div>
        </div>

        <div>
          <label className="text-sm font-medium text-slate-300">Confidence Level</label>
          <select
            {...register("confidence")}
            className="mt-2 w-full rounded-xl border border-white/10 bg-white/5 p-3 text-sm text-white outline-none transition-colors focus:border-brand-500 focus:ring-1 focus:ring-brand-500/50"
          >
            <option value="low" className="bg-slate-900">Low</option>
            <option value="medium" className="bg-slate-900">Medium</option>
            <option value="high" className="bg-slate-900">High</option>
          </select>
        </div>

        <div>
          <label className="text-sm font-medium text-slate-300">Optional comment</label>
          <textarea
            {...register("comment")}
            placeholder="Why do you agree/disagree? (e.g., traffic concerns, zoning issues)"
            className="mt-2 h-24 w-full rounded-xl border border-white/10 bg-white/5 p-3 text-sm text-white outline-none transition-colors focus:border-brand-500 focus:ring-1 focus:ring-brand-500/50 placeholder:text-slate-600"
          />
        </div>

        <button
          type="submit"
          disabled={status === "submitting"}
          className="w-full rounded-xl bg-brand-500 py-3.5 text-sm font-bold text-white shadow-lg shadow-brand-500/20 transition-all hover:bg-brand-400 hover:shadow-brand-500/30 disabled:cursor-not-allowed disabled:opacity-50"
        >
          {status === "submitting" ? (
            <span className="flex items-center justify-center gap-2">
              <svg className="h-4 w-4 animate-spin" viewBox="0 0 24 24">
                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
              </svg>
              Sending...
            </span>
          ) : (
            "Submit Feedback"
          )}
        </button>

        {status === "error" && (
          <p className="text-center text-sm text-red-400">Something went wrong. Please check your connection and try again.</p>
        )}
      </form>
    </div>
  );
}
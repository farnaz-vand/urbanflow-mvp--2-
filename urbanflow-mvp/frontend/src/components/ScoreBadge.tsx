import { cn } from "@/utils/cn";

export function ScoreBadge({ score, risk }: { score: number; risk: "low" | "medium" | "high" }) {
  const riskClasses = {
    low: "bg-emerald-500/20 text-emerald-300 border-emerald-400/30",
    medium: "bg-amber-500/20 text-amber-300 border-amber-400/30",
    high: "bg-red-500/20 text-red-300 border-red-400/30",
  };

  return (
    <div className="dashboard-card p-5">
      <div className="mb-4 flex items-center justify-between">
        <span className="text-sm uppercase tracking-[0.24em] text-slate-300">Urban Intelligence Score</span>
        <span className={cn("rounded-full border px-3 py-1 text-xs font-semibold uppercase tracking-wide", riskClasses[risk])}>
          {risk} risk
        </span>
      </div>
      <div className="flex items-end gap-2">
        <span className="text-6xl font-black text-white">{score}</span>
        <span className="pb-2 text-lg text-slate-400">/100</span>
      </div>
    </div>
  );
}

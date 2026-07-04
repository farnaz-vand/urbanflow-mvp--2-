export function InsightList({ insights }: { insights: string[] }) {
  return (
    <div className="dashboard-card p-5">
      <div className="mb-4">
        <h3 className="text-lg font-semibold text-white">AI Insights</h3>
        <p className="mt-1 text-sm text-slate-400">Key observations generated from the selected site and project profile.</p>
      </div>
      <ul className="space-y-3">
        {insights.map((insight, index) => (
          <li key={index} className="flex gap-3 rounded-xl border border-white/5 bg-white/5 p-3 text-sm text-slate-200">
            <span className="mt-0.5 inline-flex h-6 w-6 shrink-0 items-center justify-center rounded-full bg-brand-500/20 text-xs font-bold text-brand-200">
              {index + 1}
            </span>
            <span>{insight}</span>
          </li>
        ))}
      </ul>
    </div>
  );
}

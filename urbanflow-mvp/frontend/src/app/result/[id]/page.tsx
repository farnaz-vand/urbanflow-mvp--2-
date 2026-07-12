import Link from "next/link";
import { getScenario } from "@/lib/api";
import { ScoreBadge } from "@/components/ScoreBadge";
import { InsightList } from "@/components/InsightList";
import ScenarioPreviewMap from "@/components/ScenarioPreviewMap";
// import DecisionFeedbackForm from "@/components/DecisionFeedbackForm";
import DecisionFeedbackForm from "@/components/DecisionFeedbackForm";

export default async function ResultPage({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;
  
  let scenario = null;
  let fetchError = false;

  try {
    scenario = await getScenario(id);
  } catch (error) {
    console.error("Failed to fetch scenario:", error);
    fetchError = true;
  }

  if (fetchError || !scenario) {
    return (
      <main className="flex min-h-screen flex-col items-center justify-center bg-slate-950 px-6 text-center">
        <div className="max-w-md rounded-2xl border border-white/10 bg-white/5 p-8 backdrop-blur-md">
          <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-full bg-amber-500/10 text-amber-500">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="h-8 w-8"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z"
              />
            </svg>
          </div>
          <h2 className="mt-6 text-2xl font-bold text-white">Scenario Not Found</h2>
          <p className="mt-3 text-sm leading-6 text-slate-400">
            The scenario with ID <code className="rounded bg-white/10 px-1.5 py-0.5 text-amber-400">#{id}</code> was not found. 
            This usually happens because the in-memory H2 database was reset during server restart.
          </p>
          <div className="mt-8 flex flex-col gap-3">
            <Link
              href="/map"
              className="w-full rounded-xl bg-brand-500 py-3 text-sm font-semibold text-white transition hover:bg-brand-400"
            >
              Go to Map & Create New Scenario
            </Link>
            <Link
              href="/"
              className="w-full rounded-xl border border-white/10 bg-white/5 py-3 text-sm font-medium text-slate-200 transition hover:bg-white/10"
            >
              Back to Home
            </Link>
          </div>
        </div>
      </main>
    );
  }

  return (
    <main className="min-h-screen px-6 py-8">
      <div className="mx-auto max-w-7xl">
        <header className="mb-6 flex flex-col gap-4 rounded-2xl border border-white/10 bg-white/5 px-6 py-5 backdrop-blur-md md:flex-row md:items-center md:justify-between">
          <div>
            <div className="text-sm uppercase tracking-[0.22em] text-brand-200">
              Analysis Result #{scenario.id}
            </div>
            <h1 className="mt-2 text-3xl font-black text-white">
              Development Feasibility Output
            </h1>
            <p className="mt-2 text-sm text-slate-400">
              A startup-grade, map-first urban intelligence summary for the
              selected site.
            </p>
          </div>
          <div className="flex gap-3">
            <Link
              href="/map"
              className="rounded-xl bg-brand-500 px-4 py-3 text-sm font-semibold text-white transition hover:bg-brand-400"
            >
              New Analysis
            </Link>
            <Link
              href="/"
              className="rounded-xl border border-white/10 bg-white/5 px-4 py-3 text-sm font-medium text-slate-200 transition hover:bg-white/10"
            >
              Home
            </Link>
          </div>
        </header>

        <section className="grid gap-6 lg:grid-cols-[0.9fr_1.1fr]">
          <div className="space-y-6">
            <ScoreBadge score={scenario.score} risk={scenario.risk} />

            <div className="dashboard-card p-5">
              <h3 className="text-lg font-semibold text-white">
                Recommendation
              </h3>
              <p className="mt-3 rounded-xl border border-brand-400/15 bg-brand-500/10 p-4 text-sm leading-7 text-slate-200">
                {scenario.recommendation}
              </p>
            </div>
           {scenario.aiExplanationText && (
              <div className="rounded-xl border border-indigo-500/30 bg-indigo-500/10 p-5 shadow-lg backdrop-blur-sm transition-all hover:border-indigo-500/50">
                <div className="flex items-center gap-2 mb-3">
                  <h3 className="text-lg font-bold text-indigo-300">
                   UrbanFlow smart analysis
                  </h3>
                </div>
                <p className="text-sm leading-relaxed text-slate-200 whitespace-pre-line">
                  {scenario.aiExplanationText}
                </p>
              </div>
            )}
            <div className="dashboard-card p-5">
              <h3 className="text-lg font-semibold text-white">
                Scenario Metadata
              </h3>
              <div className="mt-4 grid gap-3 text-sm text-slate-300 sm:grid-cols-2">
                <div className="rounded-xl border border-white/10 bg-white/5 p-3">
                  <div className="text-xs uppercase tracking-wide text-slate-500">
                    Project Type
                  </div>
                  <div className="mt-1 font-medium text-white">
                    {scenario.projectType.replace("_", " ")}
                  </div>
                </div>
                <div className="rounded-xl border border-white/10 bg-white/5 p-3">
                  <div className="text-xs uppercase tracking-wide text-slate-500">
                    Coordinates
                  </div>
                  <div className="mt-1 font-medium text-white">
                    {scenario.lat.toFixed(5)}, {scenario.lng.toFixed(5)}
                  </div>
                </div>
                <div className="rounded-xl border border-white/10 bg-white/5 p-3 sm:col-span-2">
                  <div className="text-xs uppercase tracking-wide text-slate-500">
                    Description
                  </div>
                  <div className="mt-1 leading-6 text-slate-200">
                    {scenario.description ||
                      "No additional description provided."}
                  </div>
                </div>
              </div>
            </div>

            <InsightList insights={scenario.insights} />
          </div>

          <div className="space-y-6">
            <ScenarioPreviewMap
              lat={scenario.lat}
              lng={scenario.lng}
              score={scenario.score}
            />
            <DecisionFeedbackForm scenarioId={scenario.id} />
          </div>
        </section>
      </div>
    </main>
  );
}

import { cn } from "@/utils/cn";

// پشتیبانی از هر دو حالت حروف بزرگ و کوچک برای جلوگیری از باگ
type RiskLevel = "LOW" | "MEDIUM" | "HIGH" | "low" | "medium" | "high";

export function ScoreBadge({ score, risk }: { score: number; risk: RiskLevel }) {
  // نرمال‌سازی ریسک به حروف کوچک برای تطابق آسان‌تر
  const normalizedRisk = risk.toLowerCase() as "low" | "medium" | "high";

  const riskConfig = {
    low: {
      label: "Low Risk",
      textColor: "text-emerald-400",
      bgColor: "bg-emerald-500/10",
      borderColor: "border-emerald-500/30",
      glowColor: "bg-emerald-500/20",
      barColor: "bg-emerald-500",
    },
    medium: {
      label: "Moderate Risk",
      textColor: "text-amber-400",
      bgColor: "bg-amber-500/10",
      borderColor: "border-amber-500/30",
      glowColor: "bg-amber-500/20",
      barColor: "bg-amber-500",
    },
    high: {
      label: "High Risk",
      textColor: "text-red-400",
      bgColor: "bg-red-500/10",
      borderColor: "border-red-500/30",
      glowColor: "bg-red-500/20",
      barColor: "bg-red-500",
    },
  };

  const config = riskConfig[normalizedRisk] || riskConfig.medium;

  // تعیین رنگ امتیاز بر اساس مقدار عددی
  const getScoreColor = (s: number) => {
    if (s >= 75) return "text-emerald-400";
    if (s >= 50) return "text-amber-400";
    return "text-red-400";
  };

  return (
    <div className={cn(
      "relative overflow-hidden rounded-2xl border p-6 backdrop-blur-md transition-all duration-300 hover:shadow-lg",
      config.bgColor,
      config.borderColor
    )}>
      {/* افکت درخشش پس‌زمینه (Glow Effect) */}
      <div className={cn("absolute -right-10 -top-10 h-32 w-32 rounded-full blur-3xl", config.glowColor)} />

      <div className="relative z-10 flex flex-col gap-5">
        {/* هدر */}
        <div className="flex items-center justify-between">
          <span className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">
            Urban Intelligence Score
          </span>
          <span className={cn(
            "rounded-full border px-3 py-1 text-xs font-bold uppercase tracking-wide",
            config.bgColor,
            config.borderColor,
            config.textColor
          )}>
            {config.label}
          </span>
        </div>

        {/* امتیاز اصلی */}
        <div className="flex items-baseline gap-2">
          <span className={cn("text-7xl font-black tracking-tight transition-colors", getScoreColor(score))}>
            {score}
          </span>
          <span className="text-xl font-medium text-slate-500">/ 100</span>
        </div>

        {/* نوار پیشرفت بصری (Progress Bar) */}
        <div className="h-2.5 w-full rounded-full bg-slate-800/50 backdrop-blur-sm">
          <div 
            className={cn("h-full rounded-full shadow-[0_0_10px_rgba(0,0,0,0.3)] transition-all duration-1000 ease-out", config.barColor)}
            style={{ width: `${score}%` }}
          />
        </div>
        
        {/* توضیح کوتاه */}
        <p className="text-xs leading-relaxed text-slate-400">
          Based on real-time analysis of accessibility, density, and environmental indicators.
        </p>
      </div>
    </div>
  );
}
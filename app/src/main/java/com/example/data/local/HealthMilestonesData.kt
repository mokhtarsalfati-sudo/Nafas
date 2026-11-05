package com.example.data.local

import com.example.data.model.HealthMilestone

object HealthMilestonesData {
  val milestones = listOf(
    HealthMilestone(
      id = "m_20m",
      targetMinutes = 20,
      titleAr = "20 دقيقة • عودة النبض وضغط الدم",
      titleEn = "20 Minutes • Pulse & BP Normalize",
      titleFr = "20 Minutes • Pouls & Tension normalisés",
      descAr = "يعود معدل ضربات القلب وضغط الدم ودرجة حرارة الأطراف (اليدين والقدمين) إلى مستوياتها الطبيعية.",
      descEn = "Heart rate and blood pressure drop back to normal resting levels, improving peripheral circulation.",
      descFr = "La fréquence cardiaque et la pression artérielle reviennent à des niveaux normaux.",
      category = "heart"
    ),
    HealthMilestone(
      id = "m_8h",
      targetMinutes = 8 * 60,
      titleAr = "8 ساعات • انخفاض أول أكسيد الكربون",
      titleEn = "8 Hours • Carbon Monoxide Drops",
      titleFr = "8 Heures • Baisse du monoxyde de carbone",
      descAr = "ينخفض مستوى غاز أول أكسيد الكربون السام في الدم إلى النصف، وترتفع مستويات الأكسجين إلى معدلها الطبيعي.",
      descEn = "Toxic carbon monoxide in blood drops by half, allowing blood oxygen levels to return to normal.",
      descFr = "Le taux de monoxyde de carbone dans le sang diminue de moitié et l'oxygénation redevient normale.",
      category = "lungs"
    ),
    HealthMilestone(
      id = "m_24h",
      targetMinutes = 24 * 60,
      titleAr = "24 ساعة • تراجع خطر النوبات القلبية",
      titleEn = "24 Hours • Heart Attack Risk Drops",
      titleFr = "24 Heures • Baisse du risque d'infarctus",
      descAr = "تبدأ عضلة القلب في التعافي، وتتخلص الرئتان من الإفرازات والشوائب العالقة تدريجياً.",
      descEn = "Risk of sudden heart attack begins to decrease. Lungs begin clearing out smoke debris and mucus.",
      descFr = "Le risque d'infarctus commence à diminuer. Les poumons expulsent les résidus de fumée.",
      category = "heart"
    ),
    HealthMilestone(
      id = "m_48h",
      targetMinutes = 48 * 60,
      titleAr = "48 ساعة • تجدد حواس الشم والتذوق",
      titleEn = "48 Hours • Taste & Smell Regain",
      titleFr = "48 Heures • Goût & Odorat ravivés",
      descAr = "يخرج النيكوتين تماماً من مجرى الدم، وتبدأ النهايات العصبية بالتجدد فتشعر بلذة الطعام والروائح الزكية.",
      descEn = "All nicotine is eliminated from your bloodstream. Nerve endings begin to regrow, enhancing taste and smell.",
      descFr = "La nicotine est éliminée du corps. Les terminaisons nerveuses repoussent, améliorant le goût.",
      category = "sensory"
    ),
    HealthMilestone(
      id = "m_72h",
      targetMinutes = 72 * 60,
      titleAr = "72 ساعة • استرخاء القصبات الهوائية",
      titleEn = "72 Hours • Bronchial Tubes Relax",
      titleFr = "72 Heures • Bronches détendues",
      descAr = "تسترخي الشعب الهوائية وتتسع سعة الرئتين، ويصبح التنفس أعمق وأخف، وترتفع طاقة ونشاط الجسم.",
      descEn = "Bronchial breathing tubes relax and expand. Lung capacity increases, making breathing noticeably easier.",
      descFr = "Les bronches se relâchent. La capacité respiratoire augmente et le souffle devient plus ample.",
      category = "lungs"
    ),
    HealthMilestone(
      id = "m_2w",
      targetMinutes = 14 * 24 * 60,
      titleAr = "أسبوعان • تحسن الدورة الدموية والمشي",
      titleEn = "2 Weeks • Circulation & Stamina",
      titleFr = "2 Semaines • Circulation & Endurance",
      descAr = "تتحسن الدورة الدموية في سائر الجسد بنسبة ملحوظة، ويصبح صعود الدرج والمشي السريع أسهل بكثير.",
      descEn = "Overall blood circulation improves significantly. Walking, stair-climbing, and physical exertion feel easier.",
      descFr = "La circulation sanguine globale s'améliore nettement. Marcher et monter les escaliers devient aisé.",
      category = "energy"
    ),
    HealthMilestone(
      id = "m_1m",
      targetMinutes = 30 * 24 * 60,
      titleAr = "شهر واحد • تجدد أهداب الرئة النقية",
      titleEn = "1 Month • Cilia Re-growth",
      titleFr = "1 Mois • Régénération des cils bronchiques",
      descAr = "تنمو الأهداب الرئوية المسؤولة عن تنظيف المجاري التنفسية، وتقل نوبات السعال وضيق التنفس تدريجياً.",
      descEn = "Microscopic lung cilia regrow, cleaning the lungs properly and drastically reducing coughing and shortness of breath.",
      descFr = "Les cils bronchiques repoussent, nettoyant les poumons et diminuant la toux et l'essoufflement.",
      category = "lungs"
    ),
    HealthMilestone(
      id = "m_3m",
      targetMinutes = 90 * 24 * 60,
      titleAr = "3 أشهر • ارتفاع كفاءة الرئتين بنسبة 30%",
      titleEn = "3 Months • 30% Lung Function Boost",
      titleFr = "3 Mois • Capacité pulmonaire +30%",
      descAr = "تزداد وظائف الرئتين بنسبة تصل إلى 30%، وتتحسن جودة النوم وتختفي التنهدات الثقيلة وضيق الصدر.",
      descEn = "Lung function increases by up to 30%. Deep restorative sleep improves and chest tightness disappears.",
      descFr = "La fonction pulmonaire augmente jusqu'à 30%. La qualité du sommeil s'améliore grandement.",
      category = "lungs"
    ),
    HealthMilestone(
      id = "m_6m",
      targetMinutes = 180 * 24 * 60,
      titleAr = "6 أشهر • اختفاء احتقان الجيوب الأنفية",
      titleEn = "6 Months • Clear Sinuses & Stress Relief",
      titleFr = "6 Mois • Sinus libérés & Sérénité",
      descAr = "ينحسر احتقان الجيوب الأنفية المزمن، ويتعامل الجهاز العصبي مع التوتر اليومي بهدوء دون الحاجة لسيجارة.",
      descEn = "Sinus congestion and daily shortness of breath are significantly resolved. Emotional coping stabilizes.",
      descFr = "La congestion des sinus diminue. Votre système nerveux gère le stress avec calme et équilibre.",
      category = "brain"
    ),
    HealthMilestone(
      id = "m_1y",
      targetMinutes = 365 * 24 * 60,
      titleAr = "سنة كاملة • انخفاض خطر أمراض القلب بالنصف",
      titleEn = "1 Year • Heart Disease Risk Halved",
      titleFr = "1 An • Risque cardiaque divisé par deux",
      descAr = "ينخفض خطر الإصابة بأمراض الشرايين التاجية والنوبات القلبية بمقدار النصف مقارنة بشخص لا يزال يدخن.",
      descEn = "Your excess risk of coronary heart disease drops to half that of a continuing smoker.",
      descFr = "Le risque de maladie coronarienne et d'infarctus est réduit de moitié par rapport à un fumeur.",
      category = "heart"
    ),
    HealthMilestone(
      id = "m_5y",
      targetMinutes = 5 * 365 * 24 * 60,
      titleAr = "5 سنوات • انخفاض خطر السكتة الدماغية",
      titleEn = "5 Years • Stroke Risk Normalized",
      titleFr = "5 Ans • Risque d'AVC normalisé",
      descAr = "يتراجع خطر الإصابة بالسكتات الدماغية ليصبح مساوياً لشخص لم يدخن في حياته من قبل.",
      descEn = "Your risk of suffering a stroke falls to that of someone who has never smoked in their life.",
      descFr = "Le risque d'accident vasculaire cérébral (AVC) devient égal à celui d'un non-fumeur.",
      category = "long_term"
    ),
    HealthMilestone(
      id = "m_10y",
      targetMinutes = 10 * 365 * 24 * 60,
      titleAr = "10 سنوات • تراجع خطر سرطان الرئة إلى النصف",
      titleEn = "10 Years • Lung Cancer Risk Halved",
      titleFr = "10 Ans • Risque de cancer pulmonaire divisé par deux",
      descAr = "يهبط خطر الوفاة بسرطان الرئة إلى النصف مقارنة بالمدخن، وتتعافى خلايا الحنجرة والمريء والمثانة.",
      descEn = "Your risk of dying from lung cancer is halved compared to a smoker. Precancerous cells have been replaced.",
      descFr = "Le risque de mortalité par cancer du poumon est divisé par deux comparativement à un fumeur.",
      category = "long_term"
    )
  )
}

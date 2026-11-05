package com.example.data.local

import com.example.data.model.Achievement
import com.example.data.model.AchievementType

object AchievementsData {
  val badges = listOf(
    // Time Badges
    Achievement(
      id = "ach_time_24h",
      type = AchievementType.TIME_HOURS,
      threshold = 24.0,
      titleAr = "أول نفس نقي",
      titleEn = "First Pure Breath",
      titleFr = "Premier Souffle Pur",
      descAr = "إتمام 24 ساعة كاملة دون تدخين وتطهير الدم من أول أكسيد الكربون.",
      descEn = "Completed 24 hours smoke-free, allowing carbon monoxide to leave your blood.",
      descFr = "24 heures complètes sans tabac, le monoxyde de carbone s'évacue.",
      iconEmoji = "🌱"
    ),
    Achievement(
      id = "ach_time_3d",
      type = AchievementType.TIME_HOURS,
      threshold = 72.0,
      titleAr = "قاهر النيكوتين",
      titleEn = "Nicotine Slayer",
      titleFr = "Vainqueur de la Nicotine",
      descAr = "إتمام 3 أيام وتجاوز المرحلة الأشد من أعراض الانسحاب الجسدي بنجاح.",
      descEn = "Completed 3 days: physical nicotine is completely out of your body system.",
      descFr = "3 jours sans tabac : la dépendance physique immédiate est surmontée.",
      iconEmoji = "🛡️"
    ),
    Achievement(
      id = "ach_time_7d",
      type = AchievementType.TIME_HOURS,
      threshold = 168.0,
      titleAr = "إرادة حديدية",
      titleEn = "Iron Will",
      titleFr = "Volonté de Fer",
      descAr = "أسبوع كامل من الصبر والعزيمة العالية. تضاعفت حاسة التذوق وتحسن النوم.",
      descEn = "One full week of steadfast determination. Taste buds and sleep quality restored.",
      descFr = "Une semaine complète de persévérance et de renouveau sensoriel.",
      iconEmoji = "⚡"
    ),
    Achievement(
      id = "ach_time_14d",
      type = AchievementType.TIME_HOURS,
      threshold = 336.0,
      titleAr = "نبض الحياة",
      titleEn = "Pulse of Life",
      titleFr = "Pulsation de Vie",
      descAr = "أسبوعان من التدفق الدموي الطبيعي وسهولة صعود الدرج والنشاط.",
      descEn = "Two weeks smoke-free. Physical exertion and oxygenation feel effortless.",
      descFr = "Deux semaines : oxygénation et aisance à la marche retrouvées.",
      iconEmoji = "❤️"
    ),
    Achievement(
      id = "ach_time_30d",
      type = AchievementType.TIME_HOURS,
      threshold = 720.0,
      titleAr = "شهر النقاء المبارك",
      titleEn = "One Month of Purity",
      titleFr = "Un Mois de Pureté",
      descAr = "شهر كامل! نمت أهداب الرئة وزالت العادة القديمة وحل محلها النقاء.",
      descEn = "One full month. Lung cilia regrowing, old routine replaced with pure air.",
      descFr = "Un mois complet sans tabac. Les cils bronchiques se régénèrent.",
      iconEmoji = "🌟"
    ),
    Achievement(
      id = "ach_time_90d",
      type = AchievementType.TIME_HOURS,
      threshold = 2160.0,
      titleAr = "سيد الرئتين",
      titleEn = "Master of Lungs",
      titleFr = "Maître du Souffle",
      descAr = "3 أشهر كاملة. سعة الرئتين ارتفعت بنسبة تقارب 30% والحرية أصبحت واقعاً.",
      descEn = "3 months smoke-free. Lung capacity up by ~30%, psychological independence solidified.",
      descFr = "3 mois : capacité respiratoire augmentée d'environ 30%.",
      iconEmoji = "🫁"
    ),
    Achievement(
      id = "ach_time_180d",
      type = AchievementType.TIME_HOURS,
      threshold = 4320.0,
      titleAr = "نصف عام من المجد",
      titleEn = "Half-Year Glory",
      titleFr = "Six Mois de Liberté",
      descAr = "6 أشهر من الصحة. انحسار تام للاحتقان وتحسن المناعة الشاملة.",
      descEn = "6 months smoke-free. Sinuses completely cleared, overall immune vitality.",
      descFr = "Six mois de santé retrouvée et immunité renforcée.",
      iconEmoji = "🏆"
    ),
    Achievement(
      id = "ach_time_365d",
      type = AchievementType.TIME_HOURS,
      threshold = 8760.0,
      titleAr = "تاج الحرية المطلقة",
      titleEn = "Crown of Absolute Freedom",
      titleFr = "Couronne de Liberté Absolue",
      descAr = "سنة كاملة! انخفض خطر أمراض القلب بنسبة 50%. أنت إنسان جديد بكل فخر.",
      descEn = "One full year! Heart disease risk halved. You are a transformed champion.",
      descFr = "Un an complet ! Le risque de maladie cardiaque est réduit de moitié.",
      iconEmoji = "👑"
    ),

    // Money Badges
    Achievement(
      id = "ach_money_50",
      type = AchievementType.MONEY_SAVED,
      threshold = 50.0,
      titleAr = "الدرهم المبارك",
      titleEn = "First Saved Currency",
      titleFr = "Premières Économies",
      descAr = "وفرت أول 50 وحدة نقدية في جيبك لصالح أهلك وصحتك.",
      descEn = "Preserved your first 50 units of money for wholesome blessings.",
      descFr = "Vos 50 premières unités d'argent épargnées avec succès.",
      iconEmoji = "🪙"
    ),
    Achievement(
      id = "ach_money_500",
      type = AchievementType.MONEY_SAVED,
      threshold = 500.0,
      titleAr = "مستثمر الصحة",
      titleEn = "Health Investor",
      titleFr = "Investisseur Santé",
      descAr = "وفرت أكثر من 500 وحدة نقدية كانت ستحترق رماداً وتضر جسدك.",
      descEn = "Over 500 currency units saved from burning into ash and toxins.",
      descFr = "Plus de 500 unités épargnées pour des projets constructifs.",
      iconEmoji = "💰"
    ),
    Achievement(
      id = "ach_money_2000",
      type = AchievementType.MONEY_SAVED,
      threshold = 2000.0,
      titleAr = "خزينة البركة الكبرى",
      titleEn = "Treasury of Blessings",
      titleFr = "Trésor de Bénédiction",
      descAr = "توفير أكثر من 2000 وحدة نقدية يكفي لشراء هدية فاخرة أو مساهمة في رحلة مباركة.",
      descEn = "Over 2,000 currency units preserved: enough for a major gift or trip.",
      descFr = "Plus de 2 000 unités épargnées : assez pour un magnifique projet.",
      iconEmoji = "💎"
    ),

    // Craving Badges
    Achievement(
      id = "ach_craving_1",
      type = AchievementType.CRAVINGS_OVERCOME,
      threshold = 1.0,
      titleAr = "درع العزيمة",
      titleEn = "Shield of Resolve",
      titleFr = "Bouclier de Volonté",
      descAr = "تغلبت على أول رغبة ملحة وأثبتت لنفسك أنك أنت القائد.",
      descEn = "Defeated your first urgent craving wave, proving you are in control.",
      descFr = "Première envie surmontée avec succès.",
      iconEmoji = "🛡️"
    ),
    Achievement(
      id = "ach_craving_5",
      type = AchievementType.CRAVINGS_OVERCOME,
      threshold = 5.0,
      titleAr = "فارس الصبر",
      titleEn = "Knight of Patience",
      titleFr = "Chevalier de la Patience",
      descAr = "قهرت 5 رغبات ملحة دون استسلام واستعنت بالهدوء والتنفس.",
      descEn = "Overcame 5 craving episodes with composure and breathing.",
      descFr = "5 envies domptées avec sang-froid et calme.",
      iconEmoji = "⚔️"
    ),
    Achievement(
      id = "ach_craving_20",
      type = AchievementType.CRAVINGS_OVERCOME,
      threshold = 20.0,
      titleAr = "صخرة الثبات",
      titleEn = "Rock of Steadfastness",
      titleFr = "Roc de Constance",
      descAr = "20 رغبة هُزمت! أصبحت عصياً على الانكسار أمام العادة السابقة.",
      descEn = "20 cravings conquered! You are firmly unshakable.",
      descFr = "20 envies vaincues ! Votre maîtrise de soi est exemplaire.",
      iconEmoji = "⛰️"
    ),

    // Breathing Badges
    Achievement(
      id = "ach_breath_1",
      type = AchievementType.BREATHING_SESSIONS,
      threshold = 1.0,
      titleAr = "واحة الأكسجين الأولى",
      titleEn = "First Oxygen Oasis",
      titleFr = "Première Oasis d'Oxygène",
      descAr = "أتممت أول جلسة تنفس واعي لتهدئة ضربات القلب وضبط الذهن.",
      descEn = "Completed your first conscious breathing session to ground your nervous system.",
      descFr = "Première séance de respiration guidée terminée.",
      iconEmoji = "🧘"
    ),
    Achievement(
      id = "ach_breath_5",
      type = AchievementType.BREATHING_SESSIONS,
      threshold = 5.0,
      titleAr = "حارس السكينة",
      titleEn = "Guardian of Stillness",
      titleFr = "Gardien de la Sérénité",
      descAr = "5 جلسات تنفس مكتملة. التنفس العميق أصبح سلاحك الدائم للسكينة.",
      descEn = "5 breathing sessions completed. Deep rhythmic breathing is your natural superpower.",
      descFr = "5 séances de respiration complétées pour une paix intérieure durable.",
      iconEmoji = "🕊️"
    )
  )
}

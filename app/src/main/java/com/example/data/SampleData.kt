package com.example.data

object SampleData {

    val categories = listOf(
        Category(
            id = "siman",
            name = "سیمان",
            iconName = "cement",
            description = "سیمان سیاه، پرتلند تیپ ۲ و ۵، سیمان سفید با قیمت درب کارخانه",
            itemCount = 14
        ),
        Category(
            id = "ajr-o-sfal",
            name = "آجر و سفال",
            iconName = "brick",
            description = "تیغه سفال، آجر فشاری، آجر ۱۰ سوراخ، لفتون و آجر نسوز نما",
            itemCount = 28
        ),
        Category(
            id = "blok",
            name = "بلوک ساختمانی",
            iconName = "block",
            description = "بلوک سبک لیکا، پوکه‌ای، بلوک سیمانی ته بسته و هبلکس AAC",
            itemCount = 19
        ),
        Category(
            id = "btn",
            name = "بتن آماده",
            iconName = "concrete",
            description = "بتن آماده استاندارد با عیار ۳۰۰، ۳۵۰، ۴۰۰ همراه تراک میکسر و پمپ",
            itemCount = 8
        ),
        Category(
            id = "shn-o-mash",
            name = "شن و ماسه",
            iconName = "sand",
            description = "ماسه دوبار شور، شن بادامی، شن نخودی و ملاتی به صورت تنی و کامیونی",
            itemCount = 12
        ),
        Category(
            id = "gch",
            name = "گچ و خاک",
            iconName = "plaster",
            description = "گچ سفیدکاری، گچ و خاک آماده، گچ سوپر و گچ گیپتون ضد رطوبت",
            itemCount = 16
        ),
        Category(
            id = "chsb-kashi-o-sramik",
            name = "چسب کاشی و سرامیک",
            iconName = "adhesive",
            description = "چسب پودری پرسلان، چسب خمیری، پودر بندکشی ضد آب و رزین",
            itemCount = 22
        ),
        Category(
            id = "aizogam",
            name = "ایزوگام و عایق",
            iconName = "insulation",
            description = "ایزوگام صادراتی دولایه فویل‌دار دلیجان، قیر خالص و پرایمر",
            itemCount = 11
        ),
        Category(
            id = "tirchh-milgrd",
            name = "تیرچه و میلگرد",
            iconName = "rebar",
            description = "میلگرد آجدار کارخانه‌ها، تیرچه پاشنه بتنی و کرومیت استاندارد",
            itemCount = 25
        ),
        Category(
            id = "podr-sng",
            name = "پودر سنگ و آهک",
            iconName = "stone_powder",
            description = "پودر سنگ جوشقان، پودر سنگ سیلیسی، آهک هیدراته و فله",
            itemCount = 9
        )
    )

    val products = listOf(
        // سیمان
        Product(
            id = "sim-01",
            title = "سیمان تیپ دو ممتازان",
            categoryId = "siman",
            categoryName = "سیمان",
            price = 416_000,
            oldPrice = 445_000,
            unit = "کیسه ۵۰ کیلویی",
            brand = "سیمان ممتازان کرمان",
            rating = 4.9f,
            salesCount = 540,
            minOrder = 10,
            stockAvailable = true,
            description = "سیمان پرتلند تیپ ۲ ممتازان کرمان با مقاومت اولیه و نهایی بالا، مناسب برای ساخت سازه‌های بتنی، فونداسیون، پل‌ها و ملات‌های عمومی ساختمانی.",
            specs = mapOf(
                "نوع سیمان" to "پرتلند تیپ ۲",
                "وزن خالص" to "۵۰ کیلوگرم",
                "مقاومت ۲۸ روزه" to "۴۲۵ کیلوگرم بر سانتی‌متر مربع",
                "استاندارد" to "استاندارد ملی ایران ۳۸۹",
                "محل بارگیری" to "درب کارخانه ممتازان کرمان"
            ),
            hasFreeDeliveryInKerman = false,
            recommendedVehicle = "خاور (۸۰ کیسه) یا کمپرسی"
        ),
        Product(
            id = "sim-02",
            title = "سیمان کرمان تیپ دو",
            categoryId = "siman",
            categoryName = "سیمان",
            price = 427_000,
            oldPrice = 450_000,
            unit = "کیسه ۵۰ کیلویی",
            brand = "گروه صنایع سیمان کرمان",
            rating = 4.8f,
            salesCount = 420,
            minOrder = 10,
            stockAvailable = true,
            description = "سیمان خاکستری پرتلند تیپ ۲ کارخانه سیمان کرمان، شناخته‌شده‌ترین سیمان جنوب شرق با گیرش متعادل و مقاومت سایشی عالی.",
            specs = mapOf(
                "نوع سیمان" to "پرتلند معمولی تیپ ۲",
                "بسته‌بندی" to "کیسه پاکتی کاغذی مقاوم",
                "وزن" to "۵۰ کیلوگرم",
                "کاربرد" to "بتن‌ریزی‌های عمومی و ساختمانی"
            ),
            hasFreeDeliveryInKerman = true,
            recommendedVehicle = "خاور یا تک کمپرسی"
        ),
        Product(
            id = "sim-03",
            title = "سیمان سفید اصفهان صادراتی",
            categoryId = "siman",
            categoryName = "سیمان",
            price = 245_000,
            oldPrice = 270_000,
            unit = "کیسه ۴۰ کیلویی",
            brand = "سیمان سفید اصفهان",
            rating = 4.9f,
            salesCount = 210,
            minOrder = 5,
            stockAvailable = true,
            description = "سیمان سفید درجه یک با درجه سفیدی بالای ۹۰ درصد، مناسب نماکاری، بندکشی کاشی و قطعات پیش‌ساخته دکوراتیو.",
            specs = mapOf(
                "درجه سفیدی" to "۹۱ درصد",
                "وزن" to "۴۰ کیلوگرم",
                "استاندارد" to "ISIRI 1253"
            ),
            hasFreeDeliveryInKerman = false,
            recommendedVehicle = "وانت نیسان"
        ),

        // آجر و سفال
        Product(
            id = "ajr-01",
            title = "آجر ۷ سانتی‌متر ۱۰ سوراخ لفتون",
            categoryId = "ajr-o-sfal",
            categoryName = "آجر و سفال",
            price = 6_000,
            oldPrice = 6_800,
            unit = "قالب",
            brand = "آجر اصفهان و یزد",
            rating = 4.7f,
            salesCount = 1800,
            minOrder = 1000,
            stockAvailable = true,
            description = "آجر ۱۰ سوراخ زرد درجه یک مناسب دیوارچینی پیرامونی، سوله و حصارکشی با پخت یکدست و مقاومت فشاری استاندارد.",
            specs = mapOf(
                "ابعاد" to "۲۱.۵ × ۱۰ × ۷ سانتی‌متر",
                "تعداد در متر مربع" to "حدود ۴۲ قالب در دیوار ۱۰ سانتی",
                "وزن هر قالب" to "حدود ۲ کیلوگرم",
                "ظرفیت خاور" to "۲۵۰۰ تا ۳۰۰۰ قالب"
            ),
            hasFreeDeliveryInKerman = false,
            recommendedVehicle = "خاور یا کمپرسی ده چرخ"
        ),
        Product(
            id = "ajr-02",
            title = "آجر طرح قزاقی ۱۰*۲۰*۵ با حمل رایگان",
            categoryId = "ajr-o-sfal",
            categoryName = "آجر و سفال",
            price = 10_000,
            oldPrice = 11_500,
            unit = "قالب",
            brand = "قزاق سنتی کویر",
            rating = 4.9f,
            salesCount = 650,
            minOrder = 500,
            stockAvailable = true,
            description = "آجر سنتی قزاقی طرح کهن با کیفیت پخت کوره بالا و رنگ زرد پوستی طبیعی، مناسب نمای سنتی، ویلا و رستوران‌های سنتی با حمل رایگان در سراسر شهر کرمان.",
            specs = mapOf(
                "ابعاد" to "۲۰ × ۱۰ × ۵ سانتی‌متر",
                "رنگ" to "زرد اخرایی طبیعی",
                "حمل" to "رایگان در شهر کرمان",
                "جذب آب" to "استاندارد زیر ۱۲ درصد"
            ),
            hasFreeDeliveryInKerman = true,
            recommendedVehicle = "خاور مسقف"
        ),
        Product(
            id = "ajr-03",
            title = "تیغه ۱۰ سفالسازان درجه یک",
            categoryId = "ajr-o-sfal",
            categoryName = "آجر و سفال",
            price = 10_500,
            oldPrice = 11_800,
            unit = "قالب",
            brand = "مجتمع سفالسازان",
            rating = 4.9f,
            salesCount = 3200,
            minOrder = 500,
            stockAvailable = true,
            description = "تیغه سفالی ۱۰ سانتی با عایق صوت و حرارت استاندارد، وزن سبک و لبه‌های گونیا جهت تیغه‌بندی داخلی آپارتمان‌ها.",
            specs = mapOf(
                "ابعاد" to "۲۰ × ۲۰ × ۱۰ سانتی‌متر",
                "تعداد در متر مربع" to "۲۵ قالب",
                "وزن" to "حدود ۲.۱ کیلوگرم",
                "ضخامت جداره" to "استاندارد مهندسی"
            ),
            hasFreeDeliveryInKerman = true,
            recommendedVehicle = "خاور (۱۵۰۰ قالب) یا ده چرخ (۳۰۰۰ قالب)"
        ),
        Product(
            id = "ajr-04",
            title = "تیغه ۸ سفالسازان",
            categoryId = "ajr-o-sfal",
            categoryName = "آجر و سفال",
            price = 10_000,
            oldPrice = 11_000,
            unit = "قالب",
            brand = "مجتمع سفالسازان",
            rating = 4.7f,
            salesCount = 1400,
            minOrder = 500,
            stockAvailable = true,
            description = "سفال تیغه ۸ برای دیوارهای سرویس بهداشتی و تیغه‌های کم‌ضخامت داخل واحد جهت افزایش متراژ مفید آپارتمان.",
            specs = mapOf(
                "ابعاد" to "۲۰ × ۲۰ × ۸ سانتی‌متر",
                "تعداد در متر مربع" to "۲۵ قالب",
                "وزن" to "۱.۹ کیلوگرم"
            ),
            hasFreeDeliveryInKerman = false,
            recommendedVehicle = "خاور"
        ),
        Product(
            id = "ajr-05",
            title = "آجر فشاری زرد هوفمان (ارسال مستقیم کامیونی)",
            categoryId = "ajr-o-sfal",
            categoryName = "آجر و سفال",
            price = 2_100_000,
            oldPrice = 2_350_000,
            unit = "هزار قالب (تن معادل)",
            brand = "کوره هوفمان کرمان",
            rating = 4.8f,
            salesCount = 980,
            minOrder = 2,
            stockAvailable = true,
            description = "آجر فشاری کوره هوفمان با ابعاد ۱۱.۵ × ۲۲.۵ × ۷.۵ با بارگیری مستقیم از کارخانه و حمل رایگان در سراسر کرمان جهت فونداسیون، دیوار باربر و چاه.",
            specs = mapOf(
                "ابعاد" to "۲۲.۵ × ۱۱.۵ × ۷.۵ سانتی‌متر",
                "نوع پخت" to "کوره پیوسته هوفمان",
                "حمل" to "رایگان در محدوده شهر کرمان",
                "استحکام" to "بسیار بالا و بدون شوره"
            ),
            hasFreeDeliveryInKerman = true,
            recommendedVehicle = "کمپرسی جفت یا تریلی"
        ),

        // بلوک ساختمانی
        Product(
            id = "blk-01",
            title = "بلوک سبک پوکه‌ای لیکا سایز ۱۵",
            categoryId = "blok",
            categoryName = "بلوک ساختمانی",
            price = 28_500,
            oldPrice = 31_000,
            unit = "قالب",
            brand = "لیکا کویر",
            rating = 4.8f,
            salesCount = 820,
            minOrder = 200,
            stockAvailable = true,
            description = "بلوک بتنی سبک لیکا سه جداره، ساخته شده با دانه‌های رس منبسط شده. دارای عایق حرارتی و صوتی فوق‌العاده و مقاومت در برابر آتش.",
            specs = mapOf(
                "ابعاد" to "۵۰ × ۲۰ × ۱۵ سانتی‌متر",
                "تعداد در متر مربع" to "۱۰ قالب",
                "وزن" to "حدود ۷ کیلوگرم",
                "عایق صوت" to "۴۶ دسی‌بل"
            ),
            hasFreeDeliveryInKerman = false,
            recommendedVehicle = "خاور (۶۰۰ قالب)"
        ),
        Product(
            id = "blk-02",
            title = "بلوک اتوکلاو شده هبلکس AAC درجه یک",
            categoryId = "blok",
            categoryName = "بلوک ساختمانی",
            price = 1_280_000,
            oldPrice = 1_400_000,
            unit = "متر مکعب (پالت شده)",
            brand = "هبلکس آریا",
            rating = 4.9f,
            salesCount = 410,
            minOrder = 3,
            stockAvailable = true,
            description = "بلوک بتن هوادار اتوکلاو شده هبلکس درجه یک، سرعت اجرای ۳ برابری نسبت به مصالح سنتی، بدون نیاز به گچ و خاک، زلزله‌محور و نسوز.",
            specs = mapOf(
                "ابعاد هر بلوک" to "۶۰ × ۲۵ × ۱۰ یا ۱۵ سانتی‌متر",
                "دانسیته خشک" to "۵۰۰ کیلوگرم بر متر مکعب",
                "بسته‌بندی" to "پالت شیرینگ شده شرکتی"
            ),
            hasFreeDeliveryInKerman = true,
            recommendedVehicle = "تریلی کفی یا کفی جفت"
        ),

        // بتن آماده
        Product(
            id = "btn-01",
            title = "بتن آماده استاندارد عیار ۳۵۰ (همراه با پمپ دکل)",
            categoryId = "btn",
            categoryName = "بتن آماده",
            price = 1_450_000,
            oldPrice = 1_580_000,
            unit = "متر مکعب",
            brand = "پایانه بتن آماده استاندارد کرمان",
            rating = 4.9f,
            salesCount = 1150,
            minOrder = 5,
            stockAvailable = true,
            description = "بتن آماده استاندارد آزمایشگاه‌دار با سیمان تیپ ۲ کرمان، روانی (اسلامپ) تنظیم‌شده مطابق دستور مهندس ناظر پروژه با تراک میکسر.",
            specs = mapOf(
                "عیار سیمان" to "۳۵۰ کیلوگرم در متر مکعب",
                "اسلامپ" to "۸ الی ۱۰ سانتی‌متر",
                "تراک میکسر" to "میکسر ۷ تا ۹ متر مکعبی",
                "برگه آزمایشگاه" to "همراه با نمونه‌گیری آزمایشگاهی رسمی"
            ),
            hasFreeDeliveryInKerman = true,
            recommendedVehicle = "تراک میکسر بتن"
        ),
        Product(
            id = "btn-02",
            title = "بتن آماده عیار ۴۰۰ پرمقاومت فونداسیون",
            categoryId = "btn",
            categoryName = "بتن آماده",
            price = 1_620_000,
            oldPrice = 1_750_000,
            unit = "متر مکعب",
            brand = "بتن پاسارگاد",
            rating = 4.9f,
            salesCount = 590,
            minOrder = 5,
            stockAvailable = true,
            description = "بتن پرمقاومت با فوق روان‌کننده پلی‌کربوکسیلاتی، مخصوص اجرای فونداسیون و ستون‌های سازه‌های مرتفع و مخازن آب.",
            specs = mapOf(
                "عیار سیمان" to "۴۰۰ کیلوگرم",
                "افزودنی" to "فوق روان‌کننده زودگیر/دیرگیر استاندارد"
            ),
            hasFreeDeliveryInKerman = false,
            recommendedVehicle = "تراک میکسر بتن"
        ),

        // شن و ماسه
        Product(
            id = "snd-01",
            title = "ماسه ۰۶ شکسته دوبار شور کرمان",
            categoryId = "shn-o-mash",
            categoryName = "شن و ماسه",
            price = 340_000,
            oldPrice = 370_000,
            unit = "تن (حمل کامیونی)",
            brand = "معدن شن و ماسه کویر",
            rating = 4.8f,
            salesCount = 920,
            minOrder = 5,
            stockAvailable = true,
            description = "ماسه دو بار شور طبیعی شسته شده بدون درصد خاک رس، مناسب ملات دیوارچینی، بتن‌ریزی با کیفیت و پلاستر سیمان‌کاری.",
            specs = mapOf(
                "دانه‌بندی" to "۰ الی ۶ میلی‌متر",
                "میزان خاک رس" to "کمتر از ۲ درصد",
                "تحویل" to "باسکول دیجیتال درب معدن/پروژه"
            ),
            hasFreeDeliveryInKerman = false,
            recommendedVehicle = "کمپرسی تک (۱۰ تن) یا جفت (۱۵ تن)"
        ),
        Product(
            id = "snd-02",
            title = "شن بادامی شکسته دانه‌بندی شده",
            categoryId = "shn-o-mash",
            categoryName = "شن و ماسه",
            price = 320_000,
            oldPrice = 350_000,
            unit = "تن",
            brand = "معدن کویر کرمان",
            rating = 4.7f,
            salesCount = 430,
            minOrder = 5,
            stockAvailable = true,
            description = "شن بادامی سایز ۱۲ تا ۲۵ میلی‌متر شکسته استاندارد بتن‌ریزی با جذب آب پایین و پیوستگی دانه‌بندی مهندسی.",
            specs = mapOf(
                "سایز دانه" to "۱۲ الی ۲۵ میلی‌متر",
                "مقاومت سایش" to "لس‌آنجلس استاندارد"
            ),
            hasFreeDeliveryInKerman = false,
            recommendedVehicle = "کمپرسی ده چرخ"
        ),

        // گچ و خاک
        Product(
            id = "gch-01",
            title = "گچ سفیدکاری ساوه طلایی",
            categoryId = "gch",
            categoryName = "گچ و خاک",
            price = 58_000,
            oldPrice = 64_000,
            unit = "کیسه ۳۰ کیلویی",
            brand = "گچ طلایی ساوه",
            rating = 4.9f,
            salesCount = 1350,
            minOrder = 20,
            stockAvailable = true,
            description = "گچ سفیدکاری ممتاز ساختمانی با دانه‌بندی میکرونیزه، گیرش یکدست و سفید و درخشان بدون نیاز به الک کردن مجدد.",
            specs = mapOf(
                "وزن خالص" to "۳۰ کیلوگرم",
                "زمان گیرش اولیه" to "۸ الی ۱۲ دقیقه",
                "زمان گیرش ثانویه" to "۲۵ الی ۳۰ دقیقه"
            ),
            hasFreeDeliveryInKerman = true,
            recommendedVehicle = "وانت نیسان یا خاور"
        ),
        Product(
            id = "gch-02",
            title = "گچ و خاک آماده صالح",
            categoryId = "gch",
            categoryName = "گچ و خاک",
            price = 36_000,
            oldPrice = 40_000,
            unit = "کیسه ۳۰ کیلویی",
            brand = "گچ صالح سمنان",
            rating = 4.8f,
            salesCount = 890,
            minOrder = 20,
            stockAvailable = true,
            description = "مخلوط آماده گچ و خاک پرتلند با نسبت استاندارد مهندسی، حذف مرحله اختلاط دستی خاک در کارگاه با صرفه‌جویی زمان.",
            specs = mapOf(
                "وزن" to "۳۰ کیلوگرم",
                "کاربرد" to "زیرسازی قبل از سفیدکاری نهایی"
            ),
            hasFreeDeliveryInKerman = false,
            recommendedVehicle = "خاور"
        ),

        // چسب کاشی و سرامیک
        Product(
            id = "chsb-01",
            title = "چسب کاشی پودری پرسلان شیمی ساختمان BK2F",
            categoryId = "chsb-kashi-o-sramik",
            categoryName = "چسب کاشی و سرامیک",
            price = 420_000,
            oldPrice = 460_000,
            unit = "کیسه ۲۰ کیلویی",
            brand = "شیمی ساختمان",
            rating = 5.0f,
            salesCount = 670,
            minOrder = 3,
            stockAvailable = true,
            description = "چسب پودری بر پایه سیمان تقویت شده با پلیمرهای آلمانی جهت نصب کاشی پرسلان، اسلب، سنگ‌های ابعاد بزرگ روی سطوح مختلف و گرمایش از کف.",
            specs = mapOf(
                "وزن" to "۲۰ کیلوگرم",
                "میزان مصرف" to "۳ تا ۵ کیلوگرم در هر متر مربع",
                "مقاومت چسبندگی" to "بیش از ۱.۵ مگاپاسکال",
                "استاندارد" to "C2TES1 اروپا و ایران"
            ),
            hasFreeDeliveryInKerman = true,
            recommendedVehicle = "وانت نیسان"
        ),

        // ایزوگام
        Product(
            id = "izg-01",
            title = "ایزوگام دلیجان صادراتی دو لایه فویل‌دار استاندارد",
            categoryId = "aizogam",
            categoryName = "ایزوگام و عایق",
            price = 1_180_000,
            oldPrice = 1_290_000,
            unit = "رول ۱۰ متری (۴۲ کیلو)",
            brand = "ایزوگام شرق دلیجان",
            rating = 4.9f,
            salesCount = 780,
            minOrder = 5,
            stockAvailable = true,
            description = "عایق رطوبتی پیش‌ساخته دو لایه با روکش فویل آلومینیومی ضخیم، تیشو نبافته و پلی‌استر سوزنی برای پشت‌بام، سرویس‌ها و استخر.",
            specs = mapOf(
                "طول رول" to "۱۰ متر",
                "عرض رول" to "۱ متر",
                "وزن هر رول" to "۴۲ کیلوگرم",
                "ضخامت" to "۴ میلی‌متر واقعی",
                "ضمانت‌نامه" to "۱۰ سال بیمه‌نامه معتبر"
            ),
            hasFreeDeliveryInKerman = true,
            recommendedVehicle = "وانت یا خاور"
        )
    )

    val suppliers = listOf(
        StoreSupplier(
            id = "sup-01",
            name = "انبار مرکزی مصالح مارکت کرمان",
            rating = 4.9f,
            reviewsCount = 142,
            phone = "09137465238",
            address = "کرمان، بلوار امام حسن مجتبی، نرسیده به کوچه ۱۵",
            city = "کرمان",
            isVerified = true,
            specialties = listOf("تحویل فوری کمتر از ۳ ساعت", "تضمین اصالت کالا", "انبار سرپوشیده سیمان و گچ")
        ),
        StoreSupplier(
            id = "sup-02",
            name = "پایانه فروش مستقیم سیمان ممتازان",
            rating = 4.8f,
            reviewsCount = 89,
            phone = "03433380001",
            address = "کیلومتر ۱۵ جاده کرمان - زرند، شهرک صنعتی شماره ۲",
            city = "کرمان",
            isVerified = true,
            specialties = listOf("قیمت درب کارخانه", "ارسال تریلی و بونکر", "سیمان پرتلند تیپ ۲ و فله")
        ),
        StoreSupplier(
            id = "sup-03",
            name = "مجتمع تولیدی آجر و سفال هوفمان",
            rating = 4.7f,
            reviewsCount = 67,
            phone = "03432264512",
            address = "کرمان، ابتدای جاده ماهان، منطقه صنعتی کوره پزخانه‌ها",
            city = "کرمان",
            isVerified = true,
            specialties = listOf("حمل رایگان در کرمان", "تخفیف عمده پای پروژه‌ها", "آجر فشاری و ۱۰ سوراخ")
        ),
        StoreSupplier(
            id = "sup-04",
            name = "مرکز تخصصی چسب و عایق ساختمان دلیجان",
            rating = 4.9f,
            reviewsCount = 53,
            phone = "09132418890",
            address = "کرمان، خیابان خواجو، بعد از چهارراه پروین اعتصامی",
            city = "کرمان",
            isVerified = true,
            specialties = listOf("نمایندگی رسمی شیمی ساختمان", "نصب حرفه‌ای ایزوگام با بیمه‌نامه")
        )
    )

    val sampleOrders = listOf(
        Order(
            id = "ord-101",
            orderNumber = "MSL-98421",
            date = "۱۶ آبان ۱۴۰۳ - ساعت ۱۰:۳۰",
            status = OrderStatus.IN_TRANSIT,
            items = listOf(
                CartItem(products[0], 40), // 40 کیسه سیمان
                CartItem(products[3], 1500) // 1500 قالب آجر
            ),
            totalPrice = 25_640_000,
            shippingCost = 450_000,
            discount = 1_000_000,
            finalPrice = 25_090_000,
            address = "کرمان، بلوار جمهوری، کوچه ۱۲، پلاک ۲۵ - پروژه مهندس راد",
            vehicle = "خاور کمپرسی ۴ تن",
            hasUnloader = true
        ),
        Order(
            id = "ord-102",
            orderNumber = "MSL-97812",
            date = "۱۲ آبان ۱۴۰۳",
            status = OrderStatus.DELIVERED,
            items = listOf(
                CartItem(products[8], 12), // بتن آماده
                CartItem(products[12], 8) // ایزوگام
            ),
            totalPrice = 26_840_000,
            shippingCost = 0,
            discount = 500_000,
            finalPrice = 26_340_000,
            address = "کرمان، بلوار امام رضا، نبش خیابان کوثر - برج مسکونی پردیس",
            vehicle = "تراک میکسر بتن",
            hasUnloader = false
        )
    )

    // Calculation helper formulas
    fun calculateWallMasonry(lengthMeters: Double, heightMeters: Double, wallThicknessCm: Int): CalculationResult {
        val area = lengthMeters * heightMeters
        return when (wallThicknessCm) {
            10 -> { // تیغه ۱۰ سفال
                val blocksCount = (area * 25).toInt()
                val cementBags = Math.max(1, (area * 0.15).toInt())
                val sandTons = (area * 0.05)
                val cost = (blocksCount * 10_500L) + (cementBags * 416_000L) + ((sandTons * 340_000).toLong())
                CalculationResult(
                    title = "دیوارچینی تیغه ۱۰ سانتی با سفال",
                    summary = "برای مساحت تقریبی ${String.format("%.1f", area)} متر مربع دیوار:",
                    materials = listOf(
                        CalculatedMaterialItem("تیغه ۱۰ سفالسازان", blocksCount.toString(), "قالب", products.find { it.id == "ajr-03" }),
                        CalculatedMaterialItem("سیمان تیپ ۲ ممتازان", cementBags.toString(), "کیسه ۵۰ کیلویی", products.find { it.id == "sim-01" }),
                        CalculatedMaterialItem("ماسه دوبار شور شکسته", String.format("%.2f", sandTons), "تن", products.find { it.id == "snd-01" })
                    ),
                    estimatedCost = cost
                )
            }
            15 -> { // بلوک سبک ۱۵ سانتی لیکا
                val blocksCount = (area * 10).toInt()
                val cementBags = Math.max(1, (area * 0.18).toInt())
                val sandTons = (area * 0.06)
                val cost = (blocksCount * 28_500L) + (cementBags * 416_000L) + ((sandTons * 340_000).toLong())
                CalculationResult(
                    title = "دیوارچینی با بلوک پوکه‌ای ۱۵ سانتی لیکا",
                    summary = "برای مساحت تقریبی ${String.format("%.1f", area)} متر مربع دیوار خارجی:",
                    materials = listOf(
                        CalculatedMaterialItem("بلوک سبک لیکا سایز ۱۵", blocksCount.toString(), "قالب", products.find { it.id == "blk-01" }),
                        CalculatedMaterialItem("سیمان تیپ ۲ ممتازان", cementBags.toString(), "کیسه ۵۰ کیلویی", products.find { it.id == "sim-01" }),
                        CalculatedMaterialItem("ماسه ۰۶ شکسته", String.format("%.2f", sandTons), "تن", products.find { it.id == "snd-01" })
                    ),
                    estimatedCost = cost
                )
            }
            else -> { // آجر فشاری باربر ۲۰ یا ۲۵ سانتی
                val bricksCount = (area * 90).toInt()
                val cementBags = Math.max(1, (area * 0.35).toInt())
                val sandTons = (area * 0.12)
                val cost = (bricksCount * 2_100L) + (cementBags * 416_000L) + ((sandTons * 340_000).toLong())
                CalculationResult(
                    title = "دیوارچینی سنتی با آجر فشاری",
                    summary = "برای مساحت تقریبی ${String.format("%.1f", area)} متر مربع دیوار باربر/حصار:",
                    materials = listOf(
                        CalculatedMaterialItem("آجر فشاری هوفمان", bricksCount.toString(), "قالب", products.find { it.id == "ajr-05" }),
                        CalculatedMaterialItem("سیمان تیپ ۲ ممتازان", cementBags.toString(), "کیسه ۵۰ کیلویی", products.find { it.id == "sim-01" }),
                        CalculatedMaterialItem("ماسه شکسته شسته", String.format("%.2f", sandTons), "تن", products.find { it.id == "snd-01" })
                    ),
                    estimatedCost = cost
                )
            }
        }
    }

    fun calculateConcreteSlab(areaSquareMeters: Double, thicknessCm: Double): CalculationResult {
        val volumeM3 = (areaSquareMeters * (thicknessCm / 100.0)) * 1.05 // ۵ درصد پرتی استاندارد
        val readyMixVolume = Math.ceil(volumeM3).toInt()
        val cost = readyMixVolume * 1_450_000L
        return CalculationResult(
            title = "محاسبه بتن‌ریزی دال و سقف",
            summary = "برای مساحت ${String.format("%.1f", areaSquareMeters)} متر با ضخامت $thicknessCm سانتی‌متر (با ۵٪ احتساب پرتی):",
            materials = listOf(
                CalculatedMaterialItem("بتن آماده استاندارد عیار ۳۵۰", readyMixVolume.toString(), "متر مکعب (تراک میکسر)", products.find { it.id == "btn-01" })
            ),
            estimatedCost = cost
        )
    }

    fun calculateTileWork(areaSquareMeters: Double): CalculationResult {
        val adhesiveBags = Math.max(1, Math.ceil(areaSquareMeters * 4.0 / 20.0).toInt()) // هر متر حدود ۴ کیلو چسب
        val cost = adhesiveBags * 420_000L
        return CalculationResult(
            title = "محاسبه چسب پرسلان و کاشی",
            summary = "برای مساحت ${String.format("%.1f", areaSquareMeters)} متر مربع نصب سرامیک و اسلب:",
            materials = listOf(
                CalculatedMaterialItem("چسب کاشی پودری پرسلان شیمی ساختمان", adhesiveBags.toString(), "کیسه ۲۰ کیلویی", products.find { it.id == "chsb-01" })
            ),
            estimatedCost = cost
        )
    }
}

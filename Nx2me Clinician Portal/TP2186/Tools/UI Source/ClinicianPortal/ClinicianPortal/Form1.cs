using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Diagnostics;
using System.Text.RegularExpressions;
using System.Runtime.InteropServices;
using Excel = Microsoft.Office.Interop.Excel;
using wf = System.Windows.Forms;
using System.Security.Cryptography.X509Certificates;
using System.Drawing.Text;
using System.Threading;
using System.IO;
using System.Xml;

namespace ClinicianPortal
{
    public partial class Form1 : Form
    {
        public List<string> lstErrors = new List<string>();
        public List<string> lstCenterList = new List<string>();
        public List<string> lstPIDList = new List<string>();
        public string strMessage = null;
        public static string strAppName = "Nx2me Connected Health v1.0.0";
        public string strCancelMsg = "Do you want to exit application ??";
        public static string EXCEL_FILE_PATH = Environment.CurrentDirectory + "\\Configuration\\Driven_Data\\Portal_Drive_Data_1.xlsx";
        public static string SELENIUM_SCRIPTS = Environment.CurrentDirectory + "\\ClinicianPortalAutomation.exe";
        public string strPWDError = "Passwords are required to be a minimum 8 characters and contain at least: 1 upper case letter, 1 lower case letter, 1 number, and 1 symbol(example: passW3rd!)";
        public string[] arrControlBoxErr = { "Center ID is required..!", "Center User ID is required..!","Center Password is required..!",
                                      "TestTool DB server is required..!", "TestTool PDMP web server is required..!", "URL is required..!",
                                      "Browser is required..!", "Patient ID1 is required..!", "Patient ID2 is required..!","Patient ID3 is required..!" };
        public string[] arrPID1Filter = null;
        public string[] arrPID2Filter = null;
        public string[] arrPID3Filter = null;

        /* UI and Functionality verification data sheet.*/
        public const int UI_FUNCTIONALITY_DATA = 1;

        /* Login credentials for clinicina portal.*/
        public const int CENTER_ID_ROW = 3;
        public const int USER_ID_ROW = 3;
        public const int PASSWORD_ROW = 3;

        public const int CENTER_ID_COL = 1;
        public const int USER_ID_COL = 2;
        public const int PASSWORD_COL = 3;

        /* VPN credentials for testtool.*/
        public const int VPN_USER_ID_ROW = 8;
        public const int VPN_PASSWORD_ROW = 8;

        public const int VPN_USER_ID_COL = 1;
        public const int VPN_PASSWORD_COL = 2;

        /* URL */
        public const string NX2ME_SQA_URL = "https://nx2mesqa.nxstage.com/";
        public const string NX2ME_DEV_URL = "https://nx2medev.nxstage.com/";
        public const string NX2ME_DEMO_URL = "https://nx2medemo.nxstage.com/";

        public const int URL_ROW = 7;
        public const int URL_COL = 6;

        /* Browser type.*/
        public const int BROWSER_ROW = 7;
        public const int BROWSER_COL = 5;

        /*Center ID's and Patient ID's for xml*/
        public const int CID1_ROW = 13;
        public const int CID2_ROW = 14;
        public const int CID3_ROW = 15;

        public const int CID1_COL = 1;
        public const int CID2_COL = 1;
        public const int CID3_COL = 1;

        public const int PID1_ROW = 13;
        public const int PID2_ROW = 14;
        public const int PID3_ROW = 15;

        public const int PID1_COL = 2;
        public const int PID2_COL = 2;
        public const int PID3_COL = 2;

        /*TestTool data*/
        public const int TT_HEADER_VERSION_ROW = 20;
        public const int TT_DB_SERVER_ROW = 20;
        public const int TT_PDMP_SERVER_ROW = 20;

        public const int TT_HEADER_VERSION_COL = 1;
        public const int TT_DB_SERVER_COL = 2;
        public const int TT_PDMP_SERVER_COL = 3;
		
        public static string[][] arrCenterDataCredentials = {new[]{"1234","nurse1adm@nxstage.com", "B@home4Tx","112233-Henry","12222-John","12341-Katherine","12343-Billy","12344-Ramona","12346-Laura","12347-A","12348-Doonna","12349-US","123422-Michael",
                                                                 "124377-John","124578-John L","124598-SQA","125678-George","12742-John","235689-Patient","43210-John","43211-Charlie","43212-Dill","43213-John",
                                                                 "43214-Harry","43215-Clayton","43216-Harry","43217-Sandie","43218-jhjh","43219-Test","999901-George"},
                                                           new[]{"1234UK", "nurse1adm@nxstage.co.uk", "B@home4Tx","911223-Henry","912222-John","912341-Katherine","912343-Billy","912344-Ramona","912346-Laura","912347-A","912348-Doonna","912349-US","912432-Michael",
                                                                 "912437-John","912457-John L","912459-SQA","912567-George","912742-John","923568-Patient","943210-John","943211-Charlie","943212-Dill","943213-John",
                                                                 "943214-Harry","943215-Clayton","943217-Sandie","943218-jhjh","943219-Test","999990-George"},
                                                           new[]{"222000","","","" },
                                                           new[]{"222001", "", "","" },
                                                           new[]{"5501", "user1a@nxstage.com", "B@home4Tx","55010-George","55011-Vivian","55012-Rahul","55013-Priscilla","55014-Eric","55015-Kristina","55016-Kate","55017-John","55018-Bruce","55019-Marianne"},
                                                           new[]{"5502", "user2a@nxstage.com", "B@home4Tx","55020-George","55021-Vivian","55022-Rahul","55023-Priscilla","55024-Eric","55025-Kristina","55026-Kate","55027-John","55028-Bruce","55029-Marianne"},
                                                           new[]{"5503", "user3a@nxstage.com" , "B@home4Tx","55030-George","55031-Vivian","55032-Rahul","55033-Priscilla","55034-Eric","55035-Kristina","55036-Kate","55037-John","55038-Bruce","55039-Marianne"},
                                                           new[]{"5504", "user4a@nxstage.com", "B@home4Tx","55040-George","55041-Vivian","55042-Rahul","55043-Priscilla","55044-Eric","55045-Kristina","55046-Kate","55047-John","55048-Bruce","55049-Marianne" },
                                                           new[]{"5505", "user5a@nxstage.com", "B@home4Tx","55050-George","55051-Vivian","55052-Rahul","55053-Priscilla","55054-Eric","55055-Kristina","55056-Kate","55057-John","55058-Bruce","55059-Marianne" },
                                                           new[]{"5506", "user6a@nxstage.com", "B@home4Tx","55060-George","55061-Vivian","55062-Rahul","55063-Priscilla","55064-Eric","55065-Kristina","55066-Kate","55067-John","55068-Bruce","55069-Marianne" },
                                                           new[]{"5507", "user7a@nxstage.com", "B@home4Tx","55070-George","55071-Vivian","55072-Rahul","55073-Priscilla","55074-Eric","55075-Kristina","55076-Kate","55077-John","55078-Bruce","55079-Marianne" },
                                                           new[]{"5508", "user8a@nxstage.com", "B@home4Tx","55080-George","55081-Vivian","55082-Rahul","55083-Priscilla","55084-Eric","55085-Kristina","55086-Kate","55087-John","55088-Bruce","55089-Marianne" },
                                                           new[]{"5509", "user9a@nxstage.com", "B@home4Tx","55090-George","55091-Vivian","55092-Rahul","55093-Priscilla","55094-Eric","55095-Kristina","55096-Kate","55097-John","55098-Bruce","55099-Marianne" },
                                                           new[]{ "5510", "user10a@nxstage.com", "B@home4Tx","55100-George","55101-Vivian","55102-Rahul","55103-Priscilla","55104-Eric","55105-Kristina","55106-Kate","55107-John","55108-Bruce","55109-Marianne"},
                                                           new[]{"900777", "", "", "90777.1-Sarah","907771.John","907772-Mary","907773-Chris","907774-Pete","907775-Michael","907776-Robert","907777-Jermaine","907778-Jennifer","90780-Oliver" },
                                                           new[]{"900888", "nurse1@demodialysisctr.com", "B@home4Tx", "88801.1-John","88802-Chris","88803-Michael","88804-Jermaine","88805-Jennifer","88806-Olive","88807-Maureen"},
                                                           new[]{"900901", "fmctest@nxstage.com", "B@home4Tx","11101-Myra","11110-Pamela","11111-Chris","11112-Mel","11114-Michael","11115-Gerald","11116-Jillian","11117-Stewart","11118-Nicole","11120-Marie","11122.1-Mark","11123-Jennifer","11124-George","576321-Maureen" },
                                                           new[]{"900902", "nurse1adm@nxstage.com", "B@home4Tx","22211-George","22212-Maureen","22213-John","22214-Elizabeth","22215-Stewart","22216.1-Nicole","22217-Rachel","22218-Pamela","2222.1-Jennifer","22222-Noah","678934-Mark" },
                                                           new[]{"900903", "nurse3adm@nxstage.com", "B@home4Tx","33310-Jillian","33311-Jillian","33312-Nicole","33313-Rachel","33314-Pamela","33315-Eric","33316-Patrick","33317-Caitlin","33331-James","33332-Chris","33333-John","33334-Mark","33335-Jennifer","33337-George","333372.2-Jesse","333373.6-Gerald","33338-Maureen","33339-Simon","33345-Noah","33346-Elizabeth","33347-Doris","33348-Sean","33349-Kim","33371.1-Christine","33374.3-Mel","33375.4-Michael","33376.5-Marie","33377.8-Myra","33426-Emily","33568-Frank","336542.2-Charlies","338756-Jack","44412-Stewart" },
                                                           new[]{"900904", "nurse4adm@nxstage.com", "B@home4Tx","442478.8-William","44410-Gerald","44411-Jillian","44413-Nicole","44414-Rachel","44415-Pamela","44416-Eric","44417-Patrick","44430-Caitlin Really Lo","44441-James","44442-Chris","44443-John","44444-Mel","44445-Jennifer","44446-George","44444-Maureen","444471.1-Christine","444472.2-Jesse","444473.3-Michael","444474.4-Marie","444475.5-Myra","444479.9-Simon","44448-Noah","444480.8-Doris","444481.7-Sean","44449-Kim","44568-Louis","44731-Brendan","44822-Diane","44949-Veronica","56486-Elizabeth" },
                                                           new[]{"900905", "nurse5adm@nxstage.com", "B@home4Tx","552578-Christine","552578.1-Christine","552578.2-Christine","552578.3-Christine","55423-Frank","55432.1-Frank","55510-Jillian","55511-Stewart","55512-Nicole","55513-Rachel","55514-Pamela","55515-Eric","55516-Patrick","55517-Caitlin","55517.1-Caitlin","55517.2-Caitlin","55518-James","55518.1-James","55518.2-James","55519-Lisa","55520.1-Steve","55521.1-Amy","55522.3-Ben","55523-Jamine","55524.1-Kevin","55525.1-Sarah","55526-Chris","55551-Chris","55551.1-Chris","55552-John","55553-Mel","55554-Marie","55556-Maureen","55557-Noah","555571.1-Jesse","555572.2-Gerald","555573.3-Myra","555574.4-Mark","555575.5-Jennifer","555576.6-George","555579-Doris","555579.1-Doris","555579.2-Doris","55558.1-Elizabeth","555580.5-Sean","555581.4-Kim","55559-Simon","55564.1-Emily","55564.2-Emily","55872.2-Jack","57895-Charlies","57895.1-Charlies" },
                                                           new[]{"900906", "nurse6adm@nxstage.com", "B@home4Tx","6375.6-Maureen","64674.5-George","66369-Brendan","66456-Veronica","66457-Diane","66610-Jillian","66611-Stewart","66612-Nicole","66613-Rachel","66614-Pamela","66615-Eric","66616-Patrick","66617-Caitlin","66646-James","6666.1-Jesse","66661-Chris","66662-John","66664-Michael","66665-Marie","66666-Myra","66667-Noah","666670.1-Jesse","666671.2-Gerald","666676.8-Christine","666678.3-Doris","666679.2-Sean","66668-Elizabeth","666680.1-Kim","66669-Simon","66852-Louis","66973.4-Jennifer","672.3-Mark" },
                                                           new[]{"900907", "nurse6adm@nxstage.com" , "B@home4Tx","77127.7-Frank","77234-Paula","77277-Jack","77377-Charlies","77423-Emily","77477-Louis","77577-Diane","77677-Brendan","77710-Jesse","77711-Gerald","77712-Jillian","77713-Stewart","77714-Nicole","77715-Rachel","77716-Pamela","77717-Eric","77718-Patrick","77719-Caitlin","77720-James","77721-Chris","77722-John","77723-Mel","77724-Michael","77725-Marie","77726-Myra","77727-Mark","77728-Jennifer","77729-George","77730-Maureen","77731-Noah","77771-Elizabeth","77772-Christine","77773-William","77774-Justin","77775-Sarah","77776-Sue","77777-Simon","777770.1-Doris","77778-Sean","77779-Kim","77865-Caroline","77877-Veronica","99992-Noah"},
                                                           new[]{"900908", "nurse8adm@nxstage.com", "B@home4Tx","88556-Jack","88678-nxstage","88710-Patrick","88710.1-Patrick","88712-Caitlin","88712-James","88714-John","88715-Mel","8876-Michael","88717-Marie","88718-Myra","88719-Mark","88720-Jennifer","88721-George","88722-Maureen","88723-Demo Patient","88724-Elizabeth","88776-Christine","88777-Jesse","88778-Gerald","88779-Jillian","887870.8-Charlies","88830-Stewart","88831.1-Demo Patient","88847-Doris","88849-Sean","88855-Demo Patient","88881-Rachel","88882-Eric","88889-Simon","88998-Emily" },
                                                           new[]{"900909", "nurse9adm@nxstage.com" , "B@home4Tx","9173.4-Simon","91975.6-Sean","9783.2-Louis","98971.2-Pamela","99456-Brendan","99574-Doris","995797.3-Veronica","99670.1-Rachel","99879-Diane","99910-Patrick","99911-Caitlin","99912-James","99913-Chris","99914-John","99915-Mel","99916-Michael","99917-Marie","999920-Myra","99921-Mark","99922-Jennifer","99923-George","999872.3-Eric","99991-Demo Patient","99993-Demo Patient","99994-Demo Patient","99995-Demo Patient","99996-Demo Patient","99997-Jillian","99998.1-Stewart","99999-Nicole"},
                                                           new[]{"900910", "nurse10adm@nxstage.com", "B@home4Tx","10000.5-George","100001-Demo Patient","100003-Demo Patient","100005-Rachel","10001.614-Maureen Really","10002.8-Noah","10004.Elizabeth","10004.1-Elizabeth","10004.2-Elizabeth","10004.3-Elizabeth","10101-Demo Patient","11011-Jesse","121121.1-Gerald","12187-Jillian" },
                                                           new[]{"900911", "nurse11adm@nxstage.com", "B@home4Tx","11030-Doris","111131-Noah","11121-Chris","11132-Christine","111424-Michael","11143-William","11154-Justin","11165-Sarah","11176-Sue","11178-Sean","11187-Simon","11197-Kim","11213--Stewart","112170.1-Doris","11221-Elizabeth","11222-John","112234-Jack","11232-Frank","11314-Nicole","11323-Mel","11415-Rachel","11516-Pamela","11525-Marie","11564-Emily","11617-Eric","11626-Myra","116611-Charlies","11710-Jesse","11718-Patrick","11727-Mark","11811-Gerald","11819-Caitlin","11828-Jennifer","11912-Jillian","11920-James","11929-George" },
                                                           new[]{"900912", "nurse12adm@nxstage.com", "B@home4Tx","12030-Maureen","12121-Chris","12122-Doris","121424-Michael","12178-Sean","12199-Kim","12213-Stewart","122131-Noah","12221-Elizabeth","12232-Christine","12243-William","12254-Justin","12265-Sarah","12276-Sue","12287-Simon","12314-Nicole","12323-Mel","12415-Rachel","12434-Louis","12516-Pamela","12525-Marie","12617-Myra","12710-Jesse","12718-Patrick","12727-Mark","12819-Caitlin","12887-Veronica","12912-Jillian","12920-James","12929-George","12993-Brendan","14422-John","14487-Simon" },
                                                           new[]{"900914", "nurse14adm@nxstage.com", "B@home4Tx","12262-Diane","12811-Gerald","12828-Jennifer","14121-Chris","14178-Sean","14199-Demo Patient","14213-Stewart","14225-Frank","14314-Demo Patient","14323-Mel","14332-Jack","144131-Demo Patient","14415-Rachel","14421-Elizabeth","14432-Christine","14443-William","14444-Emily","14465-Sarah","144704-Doris","144704.1-Doris","14476-Sue","14516-Demo Patient","14617-Eric","14626-Myra","14710-Jesse","14718-Demo Patient","14811-Gerald","14819-Caitlin","148875-Charles","14920-James","14929-George" },
                                                           new[]{"900915", "nurse15adm@nxstage.com", "B@home4Tx","15030-Maureen","15121-Chris","151424-Michael","15178-Sean","15199-Kim","15233-Stewart","15254-Justin","15265-Sarah","15276-Sue","15287-Simon","15314-Mel","15415-Rachel","155131-Noah","15516-Pamela","15521-Elizabeth","15522-John","15525-Marie","15532-Christine","15543-William","155755.1-Doris","15596-Veronica","15617-Eric","15626-Myra","15710-Jesse","15718-Patrick","15723-Diane","15727-Mark","15811-Gerald","15819-Caitlin","15828-Jennifer","15882-Louis","15912-Jillian","15920-James","15929-George","30002-Jennifer2","30003-Jennifer3","30004-Jennifer4","30005-Jennifer5","30006-Jennifer6","30007-Jennifer7","30008-Jennifer8","30014-Jennifer14","30015-Jennifer15","30016-Jennifer16","30017-Jennifer17","30018-Jennifer18","30019-Jennifer19","30020-Jennifer20","30021-Jennifer21","30022-Jennifer22","30023-Jennifer23","30024-Jennifer24","30025-Jennifer25","30026-Jennifer26","30027-Jennifer27","30028-Jennifer28","30029-Jennifer29","30030-Jennifer30","30031-Jennifer31","30032-Jennifer32","30033-Jennifer33","30034-Jennifer34","30035-Jennifer35","30036-Jennifer36","30037-Jennifer37","30038-Jennifer38","30039-Jennifer39","30040-Nicolas1","30041-Nicolas2","30042-Nicolas3","30043-Nicolas4","30044-Nicolas5","30045-Nicolas6","30046-Nicolas7","30047-Nicolas8","30048-Nicolas9","30049-Nicolas10","30050-Nicolas11",
                                                                 "30051-Nicolas12","30052-Nicolas13","30053-Nicolas14","30054-Nicolas15","30055-Nicolas16","30056-Nicolas17","30057-Nicolas18","30058-Nicolas19","30059-Nicolas20","30060-Nicolas21","30061-Nicolas22","30062-Nicolas23","30063-Nicolas24","30064-Nicolas25","30065-Nicolas26","30066-Nicolas27","30067-Nicolas28","30068-Nicolas29","30069-Nicolas30","30070-Nicolas31","30071-Nicolas32","30072-Nicolas33","30073-Nicolas34","30074-Nicolas35","30075-Nicolas36","30076-Nicolas37","30077-Nicolas38","30078-Nicolas39","30079-Nicolas40","30080.1-Rachel1","30081.1-Rachel2","30082.1-Rachel3","30083.1-Rachel4","30084.1-Rachel5","30085.1-Rachel6","30086.1-Rachel7","30087.1-Rachel8","30088.1-Rachel9","30089.1-Rachel10","30090.1-Rachel11","30091.1-Rachel12","30092.1-Rachel13","30093.1-Rachel14","30094.1-Rachel15","30095.1-Rachel16","30096.1-Rachel17","30097.1-Rachel18","30098.1-Rachel19","30099.1-Rachel20","30100.1-Rachel21","30101.1-Rachel22","30102.1-Rachel23","30103.1-Rachel24","301041-Rachel25","30105.1-Rachel26","30106.1-Rachel27","30107.1-Rachel28","30108.1-Rachel29","30109.1-Rachel30","30110.1-Rachel31","30111.1-Rachel32","30112.1-Rachel33","30113.1-Rachel34","30114.1-Rachel35","30115.1-Rachel36","30116.1-Rachel37","30117.1-Rachel38","30118.1-Rachel39","30119.1-Rachel40","30120.1-Noah1","30121.1-Noah2","30122.1-Noah3",
                                                                 "30123.1-Noah4","30124.1-Noah5","30125.1-Noah6","30126.1-Noah7","30127.1-Noah8","30128.1-Noah9","30129.1-Noah10","30130.1-Noah11","30131.1-Noah12","30132.1-Noah13","30133.1-Noah14","30134.1-Noah15","30135.1-Noah16","30136.1-Noah17","30137.1-Noah18","30138.1-Noah19","30139.1-Noah20","30140.1-Noah21","30141.1-Noah22","30142.1-Noah23","30143.1-Noah24","30144.1-Noah25","30145.1-Noah26","30146.1-Noah27","30147.1-Noah28","30148.1-Noah29","30149.1-Noah30","30150.1-Noah31","30151.1-Noah32","30152.1-Noah33","30153.1-Noah34","30154.1-Noah35","30155.1-Noah36","30156.1-Noah37","30157.1-Noah38","30158.1-Noah39","30159.1-Noah40","30160-Pamela1","30161-Pamela2","30162-Pamela3","30163-Pamela4","30164-Pamela5","30165-Pamela6","30166-Pamela7","30167-Pamela8","30168-Pamela9","30169-Pamela10","30170-Pamela11","30171-Pamela12","30172-Pamela13","30173-Pamela14","30174-Pamela15","30175-Pamela16","30176-Pamela17","30177-Pamela18","30178-Pamela19","30179-Pamela20","30180-Pamela21","30181-Pamela22","30182-Pamela23","30183-Pamela24","30184-Pamela25","30185-Pamela26","30186-Pamela27","30187-Pamela28","30188-Pamela29","30189-Pamela30","30190-Pamela31","30191-Pamela32","30192-Pamela33","30193-Pamela34","30194-Pamela35","30195-Pamela36","30196-Pamela37","30197-Pamela38","30198-Pamela39","30199-Pamela40" },
                                                           new[]{"900916", "nurse16adm@nxstage.com" , "B@home4Tx","161424-Michael","16199-Kim","16213-Stewart","16232-Christine","16929-George"},
                                                           new[]{ "900917","nurse17adm@nxstage.com" , "B@home4Tx","17287-Noah","17294-Christine","1731-Frank","17346-Michael","17357-Nicole","17425-Charles","174668-William","17546-Christine","17579-Jesse","176678.3-Doris","9976.8-Kim"},
                                                           new[]{"900918", "nurse18adm@nxstage.com" , "B@home4Tx","18234-Paula","185797.3-Veronica","18717-Mel","18773-William","18913-Chris","18917-Marie","18975.6-Sean","189872-Eric","18995-Jesse","18999-Nicole"},
                                                           new[]{"900918UK", "nurse18adm@nxstage.co.uk", "B@home4Tx","19234-Paula","195797.3-Veronica","19717-Eric","19723-Mel","19773-William","19913-Chris","19917-Marie","19975.6-Sean","199872-Eric","19995-Jesse","19999-Nicole" },
                                                           new[]{"900919", "nurse19adm@nxstage.com", "B@home4Tx","14030-Maureen","141424-Michael","14454-Justin","14525-Marie","14727-Mark","14828-Jennifer","14912-Jillian","19332-Sean","19612-Demo Patient","19614-Demo Patient","19742-Demo Patient","19916-John","35000-Mark0","35001-Mark1","35002-Mark2","35003-Mark3","35004-Mark4","35005-Mark5","35006-Mark6","35007-Mark7","35008-Mark8","35009-Mark9","35010-Mark10","35011-Mark11","35012-Mark12","35013-Mark13","35014-Mark14","35015-Mark15","35017-Christine","35018-Mark18","35019-Mark19","35020-Mark20","35021-Maureen1","35022-Maureen2","35023-Maureen3","35024-Maureen4","35025-Maureen5","35026-Maureen6","35027-Maureen7","35028-Maureen8","35029-Maureen9","35030-Maureen10","35031-Maureen11","35032-Maureen12","35033-Maureen13","35034-Maureen14","35035-Maureen15","35036-Maureen16","35037-Maureen17","35038-Maureen18","35039-Maureen19","35040-Maureen20",
                                                                 "35041.1-John1","35042.1-John2","35043.1-John3","35044.1-John4","35045.1-John5","35046.1-John6","35047.1-John7","35048.1-John8","35049.1-John9","35050.1-John10","35051.1-John11","35052.1-John12","35053.1-John13","35054.1-John14","35055.1-John15","35056.1-John16","35057.1-John17","35058.1-John18","35059.1-John19","35060.1-John20",
                                                                 "35061.1-Stuart1","35062.1-Stuart2","35063.1-Stuart3","35064.1-Stuart4","35065.1-Stuart5","35066.1-Stuart6","35067.1-Stuart7","35068.1-Stuart8","35069.1-Stuart9","35070.1-Stuart10","35071.1-Stuart11","35072.1-Stuart12","35073.1-Stuart13","35074.1-Stuart14","35075.1-Stuart15","35076.1-Stuart16","35077.1-Stuart17","35078.1-Stuart18","35079.1-Stuart19","35080.1-Stuart20",
                                                                 "35081-Stewart1","35082-Stewart2","35083-Stewart3","35084-Stewart4","35085-Stewart5","35086-Stewart6","35087-Stewart7","35088-Stewart8","35089-Stewart9","35090-Stewart10","35091-Stewart11","35092-Stewart12","35093-Stewart13","35094-Stewart14","35095-Stewart15","35096-Stewart16","35097-Stewart17","35098-Stewart18","35099-Stewart19","35100-Stewart20" },
                                                           new[]{"900920", "nurse20adm@nxstage.com", "B@home4Tx","20123-Charles","20436-Emily","20581-Diane","20649-Jack","20854-Brendan" },
                                                           new[]{"900921", "nurse21adm@nxstage.com", "B@home4Tx","21712-Justin" },
                                                           new[]{"900922", "nurse22adm@nxstage.com", "B@home4Tx","22378-Frank","224466-Doris","22539-Emily","22751-Maureen","22842-Kim" },
                                                           new[]{"900923", "nurse23adm@nxstage.com", "B@home4Tx","23577-Diane","23677-Brendan","23714-Nicole","23715-Rachel","23774-Justin" },
                                                           new[]{"900923UK", "nurse23adm@nxstage.co.uk", "B@home4Tx","34577-Diane","34677-Brendan","34714-Nicole","34715-Rachel","34774-Justin" },
                                                           new[]{"900024", "nurse24adm@nxstage.com", "B@home4Tx","2431-Frank","24357-Nicole","244668-William","24546-Christine","24579-Jesse" },
                                                           new[]{"900925", "nurse25adm@nxstage.com", "B@home4Tx","254433-Rachel","25577-Veronica","25634-Doris","25984-George" },
                                                           new[]{"900926", "nurse26adm@nxstage.com", "B@home4Tx","25148-Jack","262626-Gerald","26415-Brendan","26453-Michael","26688-Elizabeth","26756-Jack","269944-Frank" },
                                                           new[]{"900926UK", "nurse26adm@nxstage.co.uk", "B@home4Tx","37148-Jack","372626-Gerald","37415-Brendan","37453-Michael","37688-Elizabeth","37756-Jack","379944-Frank" },
                                                           new[]{"900927", "nurse27adm@nxstage.com" , "B@home4Tx","27147-Jesse","27258-Charles","27321-Mel","27369-Sean","27654-John","27755-Stewart","27987-Brendan"},
                                                           new[]{"900928", "nurse28adm@nxstage.com", "B@home4Tx","28147-Charles","28159-Pamela","28369-Christine","28456-Jack","28656-Brendan","286644-Emily","28712-James","28718-Myra","28753-George","28776-Christine","28855-Kim","28888-Eric" },
                                                           new[]{"900929", "nurse29adm@nxstage.com", "B@home4Tx","29710-Jennifer","29711-Frank","29712-Justin","29713-Noah","29714-Gerald","29715-Louis" },
                                                           new[]{"900930", "nurse30adm@nxstage.com", "B@home4Tx","30710-Sue","30711-Doris","30712-John","30713-Veronica","30714-Patrick","30715-Diane","88713-Chris" },
                                                           new[]{"900931", "nurse31adm@nxstage.com" , "B@home4Tx","17516-Brendan","31710-Emily","31711-Simon","31712-Amy","31713-Rachel","31714.1-Pamela","31715.1-Jennifer"},
                                                           new[]{"900932", "nurse32adm@nxstage.com" , "B@home4Tx","32710-Ben","32711-Elizabeth","32712-John","32713-Sarah","32714-Justin","32715-Lisa"},
                                                           new[]{"900933", "nurse33adm@nxstage.com" , "B@home4Tx","33030-Maureen","33265-Sarah","33718-Patrick","33727-Mark","33912-Jillian","34254.1-Justin","34828-Jennifer"},
                                                           new[]{"900934", "nurse34adm@nxstage.com", "B@home4Tx","34030-Maureen","34121-Chris","341424-Michael","34178-Sean","34199-Kim","34213-Stewart","34265-Sarah","345131-Noah","34543-William","345755.1-Doris","34585-Brendan","34596-Veronica","34617-Eric","34626-Myra","34710-Jesse","34718-Patrick","34723-Diane","34727-Mark","34819-Caitlin","34882-Louis","34912-Jillian","34920-James","34929-George" },
                                                           new[]{"900999", "nurse1adm@demodialysisctr.com" , "B@home4Tx","99901-John","99902-Chris","99903-Michael","99904-Jermaine","99905.6-Jennifer","99906-Olive","99907-Maureen"},
                                                           new[]{"A-BCD-EFG-1234567890abc-de-f-g1234567890", "nurse13adm@qa1.nxstage.com" , "B@home4Tx","13111-Pamela","13112.5-Eric","13113-Patrick","13114-Caitlin","13115-James","13116-Kate"},
                                                           new[]{"NxStage", "NxAdmin@nxstage.com", "Nx5tag3!",""} };
        public Excel.Application xlApp;
        public Excel.Workbook OxlWorkbook = null;

        public Form1()
        {
            if (AnotherInstanceExists())
            {
                MessageBox.Show("Sorry, You can not run more than one instance of this application.", strAppName + " warning..", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                System.Environment.Exit(1);
                return;
            }
            else
            {
                InitializeComponent();
            }
        }
        /***************************************************************************
         * \brief 
         ***************************************************************************/
        private void Form1_Load(object sender, EventArgs e)
        {
            EndTask("javaw");            
        }

        /***************************************************************************
        *
        * \brief   This method is used to find if the excel sheet is already in opened 
        *          state and if so, it will close the excel sheet.
        *          
        * \param   not used
        *
        * \return  None
        *
        ****************************************************************************/
        public static bool IsXlFileOpen(string xlFileName)
        {
            Excel.Application xlApp = new Excel.Application();
            Excel.Workbook xlWb = null;
            try
            {
                if (!File.Exists(xlFileName))
                {
                    wf.MessageBox.Show("Excel File does not exists!");
                    return false;
                }

                try
                {
                    xlApp = (Excel.Application)System.Runtime.InteropServices.Marshal.GetActiveObject("Excel.Application");
                }
                catch (Exception ex)
                {
                    return false;
                }

                foreach (Excel.Workbook wb in xlApp.Workbooks)
                {
                    if (wb.FullName == xlFileName)
                    {
                        xlWb = wb;
                        xlWb.Close();
                        xlApp.Quit();
                        return true;
                    }
                    else
                    {
                       //do nothing
                    }
                }

                return false;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        /***************************************************************************
         * \brief : This method is used to End the task from the taskmanger list.
         ***************************************************************************/
        public void EndTask(string taskname)
		{
           string processName = taskname.Replace(".exe", "");
           foreach (Process process in Process.GetProcessesByName(processName))
           {
               process.Kill();
           }
		}
        /***************************************************************************
         * \brief : This method is used to remove the File Attributes.
         ***************************************************************************/
        private static FileAttributes RemoveAttribute(FileAttributes attributes, FileAttributes attributesToRemove)
		{
           return attributes & ~attributesToRemove;
		}
        /***************************************************************************
         * \brief : This method is used to identify the another instance of the GUI.
         ***************************************************************************/
        public static bool AnotherInstanceExists()
		{
           Process currentRunningProcess = Process.GetCurrentProcess();
           Process[] listOfProcs = Process.GetProcessesByName(currentRunningProcess.ProcessName);
           foreach (Process proc in listOfProcs)
           {
               if ((proc.MainModule.FileName == currentRunningProcess.MainModule.FileName) && (proc.Id != currentRunningProcess.Id))
               {
                   return true;
               }
           }
           return false;
		}
        /***************************************************************************
         * \brief : This method is used to validate the password as per the password
         *          guidlines. 
         ***************************************************************************/
        private bool ValidatePassword(string passWord)
        {
           int intValidConditions = 0;
           foreach (char c in passWord)
           {
               if (c >= 'a' && c <= 'z')
               {
                   intValidConditions++;
                   break;
               }
           }
           foreach (char c in passWord)
           {
               if (c >= 'A' && c <= 'Z')
               {
                   intValidConditions++;
                   break;
               }
           }
           if (intValidConditions == 0)
           {
               return false;
           }
           foreach (char c in passWord)
           {
               if (c >= '0' && c <= '9')
               {
                   intValidConditions++;
                   break;
               }
           }
           if (intValidConditions == 1)
           {
               return false;
           }
           if (intValidConditions == 2)
           {
               char[] special = { '@', '#', '$', '%', '^', '&', '+', '=' }; // or whatever
               if (passWord.IndexOfAny(special) == -1)
               {
                   return false;
               }
           }
           return true;
        }
        /***************************************************************************
         * \brief : This method is used to validate the e-mail 
         ***************************************************************************/
        private bool Validate_Email(string email)
        {
           Regex regex = new Regex(@"^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$");
           Match match = regex.Match(email);
           if (match.Success)
           {
               return true;
           }
           else
           {
               return false;
           }
        }
        /***************************************************************************
         * 
         ***************************************************************************/
        private void _CenterIdCombo_SelectedIndexChanged(object sender, EventArgs e)
        {
           lstPIDList.Clear();
           _PID1Combo.Items.Clear();
           _PID2Combo.Items.Clear();
           _PID3Combo.Items.Clear();
           _PID1Combo.Text = "";
           _PID2Combo.Text = "";
           _PID3Combo.Text = "";

           for (int i = 0; i < arrCenterDataCredentials.Length; i++)
           {
               if (_CenterIdCombo.Text.Equals(arrCenterDataCredentials[i][0]))
               {                
                   _UserTextBox.Text = arrCenterDataCredentials[i][1];
                   _PasswordTextBox.Text = arrCenterDataCredentials[i][2];
                   for (int j = 3; j < arrCenterDataCredentials[i].Length; j++)
                   {
                        lstPIDList.Add(arrCenterDataCredentials[i][j]);
                   }
                   _PID1Combo.Items.AddRange(lstPIDList.ToArray());
                   _PID2Combo.Items.AddRange(lstPIDList.ToArray());
                   _PID3Combo.Items.AddRange(lstPIDList.ToArray());
               }
           }
        }
       /***************************************************************************
       *
       * \brief   This method is used to launch the selenium scripts
       *          
       * \param   None
       *
       * \return  None
       *
       ****************************************************************************/
        private void Launch_Scripts()
        {
            // User did not update any field in the UI
            // Going to invoke selenium scripts
            try
            {
                System.Diagnostics.ProcessStartInfo startInfo = new ProcessStartInfo();
                startInfo.CreateNoWindow = false;
                startInfo.UseShellExecute = false;
                startInfo.FileName = SELENIUM_SCRIPTS;
                startInfo.WindowStyle = ProcessWindowStyle.Hidden;
                Process exeProcess = Process.Start(startInfo);
                Thread.Sleep(5000);
            }
            catch (Exception ex)
            {
                MessageBox.Show("ERROR : " + ex, strAppName, MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                return;
            }
        }

        /***************************************************************************
         * \brief : Activity on execute click button
         ***************************************************************************/
        private void _executeBtn_Click(object sender, EventArgs e)
        {
            string[] arrControlBoxes = { _CenterIdCombo.Text, _UserTextBox.Text, _PasswordTextBox.Text, _DBServerCombo.Text,
                                       _PDMPServerCombo.Text, _URLCombo.Text, _BrowserCombo.Text, _PID1Combo.Text, _PID2Combo.Text,_PID3Combo.Text };
            try
            {
                EndTask("javaw");
                FileInfo file = new FileInfo(@EXCEL_FILE_PATH);
                FileAttributes attributes = File.GetAttributes(EXCEL_FILE_PATH);
                if ((attributes & FileAttributes.ReadOnly) == FileAttributes.ReadOnly)
                {
                    attributes = RemoveAttribute(attributes, FileAttributes.ReadOnly);
                }
                File.SetAttributes(EXCEL_FILE_PATH, attributes);
                xlApp = new Excel.Application();
                xlApp.Visible = false;
				
                if (xlApp == null)
                {
                    /* 
                     * Display message to user that Excel is not installed in the PC
                     * MessageBox.Show(Constants.ExcelInstallError, Constants.AppName);
                     */
                    Application.Exit();
                }
				
                if (_CenterIdCombo.Text != string.Empty && _UserTextBox.Text != string.Empty && _PasswordTextBox.Text != string.Empty &&
                    _DBServerCombo.Text != string.Empty && _PDMPServerCombo.Text != string.Empty && _URLCombo.Text != string.Empty &&
                    _BrowserCombo.Text != string.Empty && _PID1Combo.Text != string.Empty && _PID2Combo.Text != string.Empty && _PID3Combo.Text != string.Empty)
                {
                    if (_PID1Combo.Text == _PID2Combo.Text || _PID2Combo.Text == _PID3Combo.Text || _PID1Combo.Text == _PID3Combo.Text)
                    {
                        MessageBox.Show("PID1,PID2 and PID3 should not be same.", strAppName, MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    }
                    else
                    {
                        //Check if the excel file is already opened and will close
                        IsXlFileOpen(EXCEL_FILE_PATH);

                        xlApp.Visible = false;
                        // Open the Excel WorkBook in the temporary path for testing...                   
                        OxlWorkbook = xlApp.Workbooks.Open(EXCEL_FILE_PATH);
                        //Navigate to the 'Existing URL and Browser settings' worksheet in Excel WorkBook
                        var worksheet = OxlWorkbook.Worksheets[UI_FUNCTIONALITY_DATA];
                        worksheet.Cells[CENTER_ID_ROW, CENTER_ID_COL] = _CenterIdCombo.Text;
                        worksheet.Cells[USER_ID_ROW, USER_ID_COL] = _UserTextBox.Text;
                        worksheet.Cells[PASSWORD_ROW, PASSWORD_COL] = _PasswordTextBox.Text;
                        worksheet.Cells[TT_DB_SERVER_ROW, TT_DB_SERVER_COL] = _DBServerCombo.Text;
                        worksheet.Cells[TT_PDMP_SERVER_ROW, TT_PDMP_SERVER_COL] = _PDMPServerCombo.Text;

                        if (_URLCombo.Text.Equals("Nx2me_SQA_Url"))
                        {
                            worksheet.Cells[URL_ROW, URL_COL] = NX2ME_SQA_URL;
                        }
                        else if (_URLCombo.Text.Equals("Nx2me_Dev_Url"))
                        {
                            worksheet.Cells[URL_ROW, URL_COL] = NX2ME_DEV_URL;
                        }
                        else if (_URLCombo.Text.Equals("Nx2me_Demo_Url"))
                        {
                            worksheet.Cells[URL_ROW, URL_COL] = NX2ME_DEMO_URL;
                        }
                        else
                        {
                            worksheet.Cells[URL_ROW, URL_COL] = _URLCombo.Text;
                        }

                        worksheet.Cells[BROWSER_ROW, BROWSER_COL] = _BrowserCombo.Text;
                        worksheet.Cells[CID1_ROW, CID1_COL] = _CenterIdCombo.Text;
                        worksheet.Cells[CID2_ROW, CID2_COL] = _CenterIdCombo.Text;
                        worksheet.Cells[CID3_ROW, CID3_COL] = _CenterIdCombo.Text;

                        arrPID1Filter = _PID1Combo.Text.Split('-');
                        arrPID2Filter = _PID2Combo.Text.Split('-');
                        arrPID3Filter = _PID3Combo.Text.Split('-');

                        worksheet.Cells[PID1_ROW, PID1_COL] = arrPID1Filter[0];
                        worksheet.Cells[PID2_ROW, PID2_COL] = arrPID2Filter[0];
                        worksheet.Cells[PID3_ROW, PID3_COL] = arrPID3Filter[0];
                        worksheet.Cells[TT_DB_SERVER_ROW, TT_DB_SERVER_COL] = _DBServerCombo.Text;
                        worksheet.Cells[TT_PDMP_SERVER_ROW, TT_PDMP_SERVER_COL] = _PDMPServerCombo.Text;

                        if (_NxStageUserIdTextBox.Text != string.Empty)
                        {
                            if (Validate_Email(_NxStageUserIdTextBox.Text))
                            {
                                worksheet.Cells[VPN_USER_ID_ROW, VPN_USER_ID_COL] = _NxStageUserIdTextBox.Text;
                                if (ValidatePassword(_NxStageUsePWDTextBox.Text))
                                {
                                    worksheet.Cells[VPN_PASSWORD_ROW, VPN_PASSWORD_COL] = _NxStageUsePWDTextBox.Text;
                                }
                                else
                                {
                                    MessageBox.Show(strPWDError, strAppName, MessageBoxButtons.OK, MessageBoxIcon.Warning);
                                    return;
                                }
                            }
                            else
                            {
                                MessageBox.Show("Invalid NxStage user id or password...", strAppName, MessageBoxButtons.OK, MessageBoxIcon.Warning);
                                return;
                            }
                        }
                        _statusInfoLabel.Visible = true;
                        xlApp.DisplayAlerts = false;
                        OxlWorkbook.SaveAs(EXCEL_FILE_PATH, Excel.XlFileFormat.xlOpenXMLWorkbook, System.Reflection.Missing.Value,
                        System.Reflection.Missing.Value, false, false, Excel.XlSaveAsAccessMode.xlNoChange,
                        Excel.XlSaveConflictResolution.xlUserResolution, true,
                        System.Reflection.Missing.Value, System.Reflection.Missing.Value, System.Reflection.Missing.Value);
                        OxlWorkbook.Close(true, System.Reflection.Missing.Value, System.Reflection.Missing.Value);
                        xlApp.Quit();
                        Thread.Sleep(3000);
                        Launch_Scripts();
                        Application.ExitThread();
                    }
                }
                else
                {
                    for (int i = 0; i < arrControlBoxes.Length; i++)
                    {
                        if (string.IsNullOrEmpty(arrControlBoxes[i]))
                        {
                            lstErrors.Add(arrControlBoxErr[i]);
                        }
                    }
                    if (lstErrors.Count > 0)
                    {
                        lstErrors.Insert(0, "The following fields are empty:\n");
                        strMessage = string.Join(Environment.NewLine, lstErrors);
                        System.Media.SystemSounds.Beep.Play();
                        MessageBox.Show(strMessage, strAppName, MessageBoxButtons.OK, MessageBoxIcon.Warning);
                        lstErrors.Clear();
                        strMessage = string.Empty;
                        return;
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("ERROR : " + ex, strAppName, MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                return;
            }

        }
        /***************************************************************************
         * \brief : Cancel the form or closed the form.
         ***************************************************************************/
        private void _cancelBtn_Click_1(object sender, EventArgs e)
        {
            if (MessageBox.Show(strCancelMsg, strAppName, MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
            {
                System.Media.SystemSounds.Beep.Play();
                Application.ExitThread();
            }
        }
        /***************************************************************************/
    }
}



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.platform.win32.WinDef.*;

public class Parsing {
	final static int WM_SETTEXT = 0x000C;
	final static int VK_ENTER	= 0x0D;
	
	public static void main(String[] args)throws Exception{

		//Document doc = Jsoup.connect("http://www.aladin.co.kr/shop/common/wbest.aspx?BranchType=1&start=we_tab").get();
		//Elements titles = doc.select("a[href*=javascript]");
		//Elements titles = doc.select(".bo3");
		//Elements authors = doc.select("a[href*=/Search/wSearchResult.aspx?AuthorSearch]");
		/*
		for(Element e: titles){

			System.out.println( e.text());
		}
		for(Element e: authors){

			System.out.println( e.text());
		}
*/	
		
		HWND hwnd = User32.INSTANCE.FindWindow(null, "¹Ò¼®"); // window title
		HWND h;
		if (hwnd == null) {
			System.out.println("Excel is not running");
		}
		else{
			User32.INSTANCE.ShowWindow(hwnd, 9 );        // SW_RESTORE
			User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front
			
			h = User32.INSTANCE.FindWindowEx(hwnd, null, "RichEdit20W", null);
			SendText(h);
		}
	
		

		

	}
	
	public static void SendText(HWND hEdit) {		
	    MyUser32.INSTANCE.SendMessage(hEdit, WM_SETTEXT, 0, "test\0".toCharArray());
	    MyUser32.INSTANCE.PostMessage(hEdit, User32.WM_KEYDOWN, VK_ENTER, 0);
	    MyUser32.INSTANCE.PostMessage(hEdit, User32.WM_KEYUP, VK_ENTER, 0);
	}
	public interface MyUser32 extends User32 {
	    MyUser32 INSTANCE = (MyUser32)Native.loadLibrary("user32", MyUser32.class, W32APIOptions.DEFAULT_OPTIONS);
	    LRESULT SendMessage(HWND hWnd, int Msg, int wParam, char[] lParam);
	    int PostMessage(HWND hWnd, int msg, int wParam, int lParam); 
	}

}


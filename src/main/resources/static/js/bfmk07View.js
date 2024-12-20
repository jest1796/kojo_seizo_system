/**
 * 削除キーを押したときの確認メッセージ
 */

	const button =document.getElementById('delete');
	button.addEventListener('click', function(){
//		confirm('削除してもよろしいでしょうか？');
		const result = window.confirm("削除してもよろしいですか？"); 
		if (result){
			const form = document.forms[0];
		
		form.action = '/b-forme_Kojo/pc/207';
		form.submit();
		}
	});
	




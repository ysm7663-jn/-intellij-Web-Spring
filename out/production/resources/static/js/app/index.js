var main = {
        init : function () {
            var _this = this;
            // 생성
            $('#btn-save').on('click', function () {
                _this.save();
                });

            // 수정
            $('#btn-update').on('click', function () {
                _this.update();
            });

            // 삭제
            $('#btn-delete').on('click', function () {
                _this.delete();
            });
        },

        save : function () {
            var data = {
                title: $('#title').val(),
                author: $('#author').val(),
                content: $('#content').val()
            };

            $.ajax({
                type: 'POST',
                url: '/api/v1/posts',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function() {
                alert('글이 등록되었습니다.');
                window.location.href = '/';     // 글 등록 성곡시 메인페이지(/)로 이동
                }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        },

        update : function () {
            var data = {
                title: $('#title').val(),
                content: $('#content').val()
            };

            var id = $('#id').val();

            $.ajax({
                type: 'PUT',
                // HTTP Method : PUT
                // PostsApiController에 이미 @PutMapping으로 선언했기 때문에 여기서도 PUT으로 해야 됨
                // >> REST 규약
                // create : POST
                // Read : GET
                // Update : PUT
                // Delete : DELETE

                url: '/api/v1/posts/' + id,
                // 어느 게시글을 수정할건지 구분하기 위해 URL(/api/v1/posts/) 에 id를 추가

                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('글이 수정되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        },

        delete : function () {
            var id = $('#id').val();

            $.ajax ({
                type: 'DELETE',
                url: '/api/v1/posts/' + id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'
            }).done(function() {
                alert('글이 삭제되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
};

main.init();
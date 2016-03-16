'use strict';

describe('Controller Tests', function() {

    describe('Disease Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDisease, MockHeredoFamilyBkg;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDisease = jasmine.createSpy('MockDisease');
            MockHeredoFamilyBkg = jasmine.createSpy('MockHeredoFamilyBkg');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Disease': MockDisease,
                'HeredoFamilyBkg': MockHeredoFamilyBkg
            };
            createController = function() {
                $injector.get('$controller')("DiseaseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:diseaseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

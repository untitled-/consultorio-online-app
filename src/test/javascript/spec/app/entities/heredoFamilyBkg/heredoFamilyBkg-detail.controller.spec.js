'use strict';

describe('Controller Tests', function() {

    describe('HeredoFamilyBkg Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHeredoFamilyBkg, MockDisease, MockPatient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHeredoFamilyBkg = jasmine.createSpy('MockHeredoFamilyBkg');
            MockDisease = jasmine.createSpy('MockDisease');
            MockPatient = jasmine.createSpy('MockPatient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HeredoFamilyBkg': MockHeredoFamilyBkg,
                'Disease': MockDisease,
                'Patient': MockPatient
            };
            createController = function() {
                $injector.get('$controller')("HeredoFamilyBkgDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:heredoFamilyBkgUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
